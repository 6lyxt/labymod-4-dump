// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.ping;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.UUID;
import java.util.ArrayList;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import com.google.gson.JsonSyntaxException;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import net.labymod.api.client.network.server.ServerInfo;
import java.io.IOException;
import java.net.SocketAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.util.logging.Logging;

public class ServerPing implements AutoCloseable
{
    protected static final Logging LOGGER;
    private static final String SAMPLE_MEMBER_NAME = "sample";
    private static final String MAX_MEMBER_NAME = "max";
    private static final String ONLINE_MEMBER_NAME = "online";
    private static final String PLAYERS_MEMBER_NAME = "players";
    private static final String NAME_MEMBER_NAME = "name";
    private static final String ID_MEMBER_NAME = "id";
    private static final byte PACKET_HANDSHAKE = 0;
    private static final byte PACKET_STATUSREQUEST = 0;
    private static final byte PACKET_PING = 1;
    private static final int STATUS_HANDSHAKE = 1;
    private final String name;
    private final ServerAddress address;
    private final ServerAddress resolvedAddress;
    private final int clientVersion;
    private final Socket socket;
    private final DataOutputStream outputStream;
    private final DataInputStream inputStream;
    
    public ServerPing(final String name, final ServerAddress address, final int timeout, final int clientVersion) throws IOException {
        this.name = name;
        this.address = address;
        this.resolvedAddress = address.resolve();
        this.clientVersion = clientVersion;
        (this.socket = new Socket()).connect(this.resolvedAddress.getAddress(), timeout);
        this.inputStream = new DataInputStream(this.socket.getInputStream());
        this.outputStream = new DataOutputStream(this.socket.getOutputStream());
    }
    
    public ServerInfo retrieveServerInfo() throws IOException {
        this.writeHandshake();
        JsonObject json;
        try {
            json = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(this.requestStatusJson(), (Class)JsonObject.class);
        }
        catch (final JsonSyntaxException exception) {
            ServerPing.LOGGER.warn("Server {} sent an invalid server status json", this.address, exception);
            return ServerInfo.failed(this.name, this.address, ServerInfo.Status.CANNOT_CONNECT);
        }
        final int ping = this.getPing();
        return this.toServerInfo(json, ping);
    }
    
    protected int getPing() {
        try {
            int ping = this.requestPing();
            if (ping < 0) {
                ping = 0;
            }
            return ping;
        }
        catch (final Exception e) {
            return -1;
        }
    }
    
    private void writeHandshake() throws IOException {
        try (final ByteArrayOutputStream payload = new ByteArrayOutputStream();
             final ByteArrayOutputStream packet = new ByteArrayOutputStream();
             final DataOutputStream handshake = new DataOutputStream(payload)) {
            handshake.writeByte(0);
            ServerPingUtil.writeVarInt(handshake, this.clientVersion);
            final String host = this.resolvedAddress.getHost();
            ServerPingUtil.writeVarInt(handshake, host.length());
            handshake.writeBytes(host);
            handshake.writeShort(this.resolvedAddress.getPort());
            ServerPingUtil.writeVarInt(handshake, 1);
            final byte[] payloadArray = payload.toByteArray();
            ServerPingUtil.writeVarInt(packet, payloadArray.length);
            packet.write(payloadArray);
            this.outputStream.write(packet.toByteArray());
        }
    }
    
    private String requestStatusJson() throws IOException {
        ServerPingUtil.writeVarInt(this.outputStream, 1);
        this.outputStream.writeByte(0);
        ServerPingUtil.readVarInt(this.inputStream);
        final int id = ServerPingUtil.readVarInt(this.inputStream);
        this.validateId(id, 0);
        final int length = ServerPingUtil.readVarInt(this.inputStream);
        ServerPingUtil.io(length == -1, "Server prematurely ended stream.");
        ServerPingUtil.io(length == 0, "Server returned unexpected value.");
        final byte[] data = new byte[length];
        this.inputStream.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }
    
    private int requestPing() throws IOException {
        final long startTime = TimeUtil.getMillis();
        try (final ByteArrayOutputStream payload = new ByteArrayOutputStream();
             final ByteArrayOutputStream packet = new ByteArrayOutputStream();
             final DataOutputStream ping = new DataOutputStream(payload)) {
            ping.writeByte(1);
            ping.writeLong(Laby.labyAPI().minecraft().getRunningMillis());
            final byte[] payloadArray = payload.toByteArray();
            ServerPingUtil.writeVarInt(packet, payloadArray.length);
            packet.write(payloadArray);
            this.outputStream.write(packet.toByteArray());
        }
        ServerPingUtil.readVarInt(this.inputStream);
        final int id = ServerPingUtil.readVarInt(this.inputStream);
        this.validateId(id, 1);
        return (int)(TimeUtil.getMillis() - startTime);
    }
    
    private ServerInfo toServerInfo(final JsonObject object, final int ping) {
        try {
            final ServerInfo.Players players = this.getPlayers(object);
            final JsonObject version = object.getAsJsonObject("version");
            final int serverVersion = version.get("protocol").getAsInt();
            final boolean versionValid = serverVersion == this.clientVersion;
            final String favicon = object.has("favicon") ? object.get("favicon").getAsString() : null;
            return ServerInfo.success(this.name, this.address, ServerInfo.resourceLocationFromBase64(favicon), (favicon != null && favicon.startsWith("data:image/png;base64,")) ? favicon : null, GsonComponentSerializer.gson().deserializeFromTree(object.get("description")), players.getOnline(), players.getMax(), players.getSamples(), ping, version.get("name").getAsString(), serverVersion, versionValid);
        }
        catch (final NullPointerException exception) {
            ServerPing.LOGGER.error("Failed to convert ServerPing to ServerInfo", exception);
            return ServerInfo.failed(this.name, this.address, ServerInfo.Status.CANNOT_CONNECT);
        }
    }
    
    private ServerInfo.Players getPlayers(final JsonObject object) {
        if (!object.has("players")) {
            return ServerInfo.Players.EMPTY;
        }
        final JsonElement playersElement = object.get("players");
        if (!playersElement.isJsonObject()) {
            return ServerInfo.Players.EMPTY;
        }
        final JsonObject players = playersElement.getAsJsonObject();
        final int online = this.getInt(players, "online", 0);
        final int max = this.getInt(players, "max", 0);
        if (!players.has("sample")) {
            return new ServerInfo.Players(online, max);
        }
        final JsonElement sampleElement = players.get("sample");
        if (!sampleElement.isJsonArray()) {
            return new ServerInfo.Players(online, max);
        }
        final JsonArray sampleArray = sampleElement.getAsJsonArray();
        final int size = sampleArray.size();
        final List<ServerInfo.Player> samples = new ArrayList<ServerInfo.Player>();
        for (int i = 0; i < size; ++i) {
            final JsonObject playerObject = sampleArray.get(i).getAsJsonObject();
            final String id = this.getString(playerObject, "id");
            if (id == null) {
                ServerPing.LOGGER.error("An {} of a player sample is null (Index: {})", "id", i);
            }
            else {
                final String name = this.getString(playerObject, "name");
                if (name == null) {
                    ServerPing.LOGGER.error("A {} of a player sample is null (Index: {})", "name", i);
                }
                else {
                    samples.add(new ServerInfo.Player(UUID.fromString(id), name));
                }
            }
        }
        return new ServerInfo.Players(online, max, samples.toArray(new ServerInfo.Player[0]));
    }
    
    private void validateId(final int id, final int expected) throws IOException {
        ServerPingUtil.io(id == -1, "Server prematurely ended stream");
        ServerPingUtil.io(id != expected, "Server returned invalid packet");
    }
    
    @Override
    public void close() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
    }
    
    private int getInt(final JsonObject object, final String memberName, final int defaultValue) {
        return (object.has(memberName) && object.get(memberName).isJsonPrimitive()) ? object.get(memberName).getAsInt() : defaultValue;
    }
    
    @Nullable
    private String getString(final JsonObject object, final String memberName) {
        return (object.has(memberName) && object.get(memberName).isJsonPrimitive()) ? object.get(memberName).getAsString() : null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public DataInputStream getInputStream() {
        return this.inputStream;
    }
    
    public DataOutputStream getOutputStream() {
        return this.outputStream;
    }
    
    public ServerAddress getAddress() {
        return this.address;
    }
    
    static {
        LOGGER = Logging.create(ServerPing.class);
    }
}
