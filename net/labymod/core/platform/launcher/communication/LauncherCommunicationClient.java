// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication;

import java.util.function.Supplier;
import java.util.Iterator;
import java.lang.reflect.Constructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.window.WindowShowEvent;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import java.nio.ByteBuffer;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherWindowCreatedPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherIdentificationPacket;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.util.concurrent.task.Task;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public class LauncherCommunicationClient
{
    private static final LauncherPacketRegistry PACKET_REGISTRY;
    private static final Logging LOGGER;
    private static final Logging FALLBACK_COMMUNICATOR;
    private static final String FALLBACK_CONNECTED = "connected";
    private static final String FALLBACK_DISCONNECTED = "disconnected";
    private static final String FALLBACK_WINDOW_CREATED = "window created";
    private final String identifier;
    private final int port;
    private final LauncherPacketHandler packetHandler;
    private final List<PacketResponse<LauncherPacket>> responses;
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private Task startTask;
    private int connectionTries;
    private boolean sentWindowCreated;
    
    public LauncherCommunicationClient(final String identifier, final int port) {
        this.responses = new ArrayList<PacketResponse<LauncherPacket>>();
        this.identifier = identifier;
        this.port = port;
        this.packetHandler = new LauncherPacketHandler(this);
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    public boolean start() {
        if (this.connectionTries > 3) {
            return false;
        }
        if (this.startTask != null) {
            this.startTask.cancel();
            this.startTask = null;
        }
        try {
            this.socket = new Socket("localhost", this.port);
            this.out = this.socket.getOutputStream();
            this.in = this.socket.getInputStream();
            LauncherCommunicationClient.LOGGER.info("Connected to Launcher.", new Object[0]);
            this.sendPacket(new LauncherIdentificationPacket(this.identifier));
            if (this.sentWindowCreated) {
                this.sendPacket(new LauncherWindowCreatedPacket());
            }
            LauncherCommunicationClient.FALLBACK_COMMUNICATOR.info("connected", new Object[0]);
            this.connectionTries = 0;
            final Thread listenerThread = new Thread(() -> {
                try {
                    while (!this.socket.isClosed()) {
                        final int length = this.readInt(this.in);
                        final byte[] data = new byte[length];
                        this.in.read(data);
                        final PayloadReader reader = new PayloadReader(ByteBuffer.wrap(data));
                        final int packetId = reader.readVarInt();
                        this.readPacket(packetId, reader);
                    }
                }
                catch (final IOException e2) {
                    try {
                        LauncherCommunicationClient.FALLBACK_COMMUNICATOR.info("disconnected", new Object[0]);
                        LauncherCommunicationClient.LOGGER.warn("Disconnected from launcher. Caused by {}: {}", e2.getClass().getSimpleName(), e2.getMessage());
                        this.socket.close();
                        this.out.close();
                        this.in.close();
                        this.start();
                    }
                    catch (final IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                return;
            });
            listenerThread.start();
        }
        catch (final IOException e) {
            ++this.connectionTries;
            LauncherCommunicationClient.LOGGER.warn("Failed to connect to launcher, retrying in 3 seconds. Caused by {}: {}", e.getClass().getSimpleName(), e.getMessage());
            (this.startTask = Task.builder(this::start).delay(3L, TimeUnit.SECONDS).build()).execute();
            return false;
        }
        return true;
    }
    
    public boolean isConnected() {
        return this.socket != null && !this.socket.isClosed();
    }
    
    public void sendPacket(final LauncherPacket packet) {
        this.sendPacket(packet, (Class<LauncherPacket>)null);
    }
    
    public <T extends LauncherPacket> T sendPacket(final LauncherPacket packet, final Class<T> responseClass) {
        if (this.socket == null || this.socket.isClosed()) {
            this.connectionTries = 0;
            if (!this.start()) {
                return null;
            }
        }
        final int packetId = LauncherCommunicationClient.PACKET_REGISTRY.getIdByPacket(packet.getClass());
        if (packetId == -1) {
            throw new IllegalArgumentException("Unknown packet type: " + packet.getClass().getName());
        }
        final PacketResponse<T> response = (responseClass != null) ? new PacketResponse<T>(responseClass) : null;
        if (response != null) {
            synchronized (this.responses) {
                this.responses.add((PacketResponse<LauncherPacket>)response);
            }
        }
        try {
            final PayloadWriter writer = new PayloadWriter();
            writer.writeVarInt(packetId);
            packet.write(writer);
            final byte[] data = writer.toByteArray();
            this.out.write(this.intToBytes(data.length));
            this.out.write(data);
            this.out.flush();
        }
        catch (final IOException e) {
            LauncherCommunicationClient.LOGGER.warn("Failed to send packet {} to launcher. Caused by {}: {}", packet.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
        }
        if (response != null) {
            while (response.supplier == null && this.socket != null && !this.socket.isClosed()) {}
            return response.supplier.get();
        }
        return null;
    }
    
    @Subscribe
    public void onWindowCreated(final WindowShowEvent event) {
        if (this.sentWindowCreated) {
            return;
        }
        this.sendPacket(new LauncherWindowCreatedPacket());
        LauncherCommunicationClient.FALLBACK_COMMUNICATOR.info("window created", new Object[0]);
        this.sentWindowCreated = true;
    }
    
    private void readPacket(final int packetId, final PayloadReader reader) {
        final Class<? extends LauncherPacket> packetById = LauncherCommunicationClient.PACKET_REGISTRY.getPacketById(packetId);
        if (packetById == null) {
            LauncherCommunicationClient.LOGGER.warn("Received unknown packet from launcher with id: {}", packetId);
            return;
        }
        for (final Constructor<?> declaredConstructor : packetById.getDeclaredConstructors()) {
            if (declaredConstructor.getParameterCount() == 0) {
                try {
                    final LauncherPacket packet = (LauncherPacket)declaredConstructor.newInstance(new Object[0]);
                    packet.read(reader);
                    packet.handle(this.packetHandler);
                    final Class<? extends LauncherPacket> packetClass = packet.getClass();
                    synchronized (this.responses) {
                        for (final PacketResponse<LauncherPacket> response : this.responses) {
                            if (response.packetClass.equals(packetClass)) {
                                response.supplier = (() -> packet);
                                this.responses.remove(response);
                                break;
                            }
                        }
                    }
                }
                catch (final Exception e) {
                    LauncherCommunicationClient.LOGGER.warn("Failed to read packet {}. Caused by {}: {}", packetById.getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
                }
                return;
            }
        }
        throw new IllegalArgumentException("No no-args constructor found for packet id: " + packetId);
    }
    
    private byte[] intToBytes(int value) {
        final byte[] bytes = new byte[4];
        for (int length = bytes.length, i = 0; i < length; ++i) {
            bytes[length - i - 1] = (byte)(value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }
    
    private int byteArrayToInt(final byte[] data) {
        return (0xFF & data[0]) << 24 | (0xFF & data[1]) << 16 | (0xFF & data[2]) << 8 | (0xFF & data[3]);
    }
    
    private int readInt(final InputStream buf) throws IOException {
        final byte[] data = new byte[4];
        buf.read(data);
        return this.byteArrayToInt(data);
    }
    
    static {
        PACKET_REGISTRY = new LauncherPacketRegistry();
        LOGGER = Logging.create(LauncherCommunicationClient.class);
        FALLBACK_COMMUNICATOR = Logging.create("LauncherFallbackCommunicator");
    }
    
    private static class PacketResponse<T extends LauncherPacket>
    {
        private final Class<T> packetClass;
        private Supplier<T> supplier;
        
        public PacketResponse(final Class<T> packetClass) {
            this.packetClass = packetClass;
        }
    }
}
