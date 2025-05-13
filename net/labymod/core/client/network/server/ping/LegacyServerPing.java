// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.ping;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.network.server.ServerInfo;
import java.io.IOException;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.regex.Pattern;

public class LegacyServerPing extends ServerPing
{
    private static final Pattern PING_HOST_DELIMETER;
    private static final String PING_HOST_MAGIC = "§1";
    private static final String PING_HOST_CHANNEL = "MC|PingHost";
    private static final int PACKET_SERVER_LIST_PING = 254;
    private static final int PACKET_CUSTOM_PAYLOAD = 250;
    private static final int PACKET_KICK = 255;
    
    public LegacyServerPing(final String name, final ServerAddress address, final int timeout, final int clientVersion) throws IOException {
        super(name, address, timeout, clientVersion);
    }
    
    @Override
    public ServerInfo retrieveServerInfo() throws IOException {
        return this.pingLegacyServer();
    }
    
    private ServerInfo pingLegacyServer() throws IOException {
        this.writeLegacyHandshake();
        final DataInputStream inputStream = this.getInputStream();
        final int packetId = inputStream.readUnsignedByte();
        if (packetId != 255) {
            throw new IOException("Illegal kick packet id: " + packetId);
        }
        final String response = ServerPingUtil.readLegacyString(inputStream);
        final String[] splitted = LegacyServerPing.PING_HOST_DELIMETER.split(response);
        if (splitted.length != 6) {
            throw new IOException("Tokens count mismatch");
        }
        final String magic = splitted[0];
        if (!magic.equals("§1")) {
            throw new IOException("Magic file mismatch: " + magic);
        }
        final int protocol = Integer.parseInt(splitted[1]);
        final String clientVersion = splitted[2];
        final String motd = splitted[3];
        final int onlinePlayers = Integer.parseInt(splitted[4]);
        final int maxPlayers = Integer.parseInt(splitted[5]);
        final int ping = this.getPing();
        final ServerInfo.Players players = new ServerInfo.Players(onlinePlayers, maxPlayers);
        final CompletableResourceLocation resourceLocation = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON, true);
        return ServerInfo.success(this.getName(), this.getAddress(), resourceLocation, null, LegacyComponentSerializer.legacySection().deserialize(motd), players.getOnline(), players.getMax(), players.getSamples(), ping, clientVersion, protocol, false);
    }
    
    private void writeLegacyHandshake() throws IOException {
        try (final ByteArrayOutputStream payload = new ByteArrayOutputStream();
             final DataOutputStream output = new DataOutputStream(payload)) {
            output.writeByte(254);
            output.writeByte(1);
            this.writeCustomPayload(output);
            output.flush();
            final byte[] packetData = payload.toByteArray();
            final DataOutputStream outputStream = this.getOutputStream();
            outputStream.write(packetData);
            outputStream.flush();
        }
    }
    
    private void writeCustomPayload(final DataOutputStream output) throws IOException {
        output.writeByte(250);
        ServerPingUtil.writeLegacyString(output, "MC|PingHost");
        try (final ByteArrayOutputStream packet = new ByteArrayOutputStream();
             final DataOutputStream packetOutput = new DataOutputStream(packet)) {
            packetOutput.writeByte(74);
            final ServerAddress address = this.getAddress();
            ServerPingUtil.writeLegacyString(packetOutput, address.getHost());
            packetOutput.writeInt(address.getPort());
            final byte[] packetData = packet.toByteArray();
            output.writeShort(packetData.length);
            output.write(packetData);
        }
    }
    
    static {
        PING_HOST_DELIMETER = Pattern.compile("\u0000", 16);
    }
}
