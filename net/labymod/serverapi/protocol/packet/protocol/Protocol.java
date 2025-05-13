// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol;

import net.labymod.serverapi.protocol.payload.io.PayloadWriter;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;
import java.util.Iterator;
import net.labymod.serverapi.protocol.payload.translation.AbstractPayloadTranslationListener;
import net.labymod.serverapi.protocol.packet.protocol.execption.ProtocolException;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import com.google.gson.Gson;
import net.labymod.serverapi.protocol.packet.Packet;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class Protocol
{
    private final ProtocolService protocolService;
    private final PayloadChannelIdentifier identifier;
    @Nullable
    private final PayloadChannelIdentifier legacyIdentifier;
    private final Map<Integer, Packet> packets;
    private final Gson gson;
    
    public Protocol(final PayloadChannelIdentifier identifier) {
        this(identifier, null);
    }
    
    public Protocol(final PayloadChannelIdentifier identifier, final PayloadChannelIdentifier legacyIdentifier) {
        this.identifier = identifier;
        this.legacyIdentifier = legacyIdentifier;
        this.packets = new HashMap<Integer, Packet>();
        this.gson = new GsonBuilder().create();
        this.protocolService = ProtocolApiBridge.getProtocolApi().getProtocolService();
    }
    
    public void registerPacket(final int id, final Packet packet) {
        if (id < 0) {
            throw new ProtocolException(String.format("The %s packet cannot be registered because negative IDs are not supported", packet.getClass().getName()));
        }
        final Packet anotherPacket = this.packets.get(id);
        if (anotherPacket != null) {
            throw new ProtocolException(String.format("The %s packet has already been registered with this %s ID.", anotherPacket.getClass().getName(), id));
        }
        this.packets.put(id, packet);
    }
    
    public void registerTranslationListener(final AbstractPayloadTranslationListener translationListener) {
        this.protocolService.registerTranslationListener(translationListener);
    }
    
    @Nullable
    public <T extends Packet> T getPacket(final int id) {
        return (T)this.packets.get(id);
    }
    
    public <T extends Packet> int getPacketId(final Class<T> packetClass) {
        for (final Map.Entry<Integer, Packet> entry : this.packets.entrySet()) {
            if (entry.getValue().getClass().equals(packetClass)) {
                return entry.getKey();
            }
        }
        return -1;
    }
    
    public PayloadChannelIdentifier getIdentifier() {
        return this.identifier;
    }
    
    @Nullable
    public PayloadChannelIdentifier getLegacyIdentifier() {
        return this.legacyIdentifier;
    }
    
    public Packet readPacket(final byte[] payload) {
        return this.readPacketFromBinary(new PayloadReader(payload));
    }
    
    private Packet readPacketFromBinary(@NotNull final PayloadReader reader) {
        final int packetId = reader.readVarInt();
        Packet packet = this.getPacket(packetId);
        if (packet == null) {
            throw new ProtocolException(String.format("No packet was found with this %s ID.", packetId));
        }
        try {
            packet = (Packet)this.gson.fromJson(reader.readString(), (Class)packet.getClass());
        }
        catch (final JsonSyntaxException exception) {
            throw new ProtocolException("An error occurred while parsing the packet.", (Throwable)exception);
        }
        return packet;
    }
    
    public void sendPacket(@NotNull final Packet packet) {
        this.protocolService.sendPacket(this.identifier, this.writePacketToBinary(packet));
    }
    
    private byte[] writePacketToBinary(@NotNull final Packet packet) {
        final PayloadWriter writer = new PayloadWriter();
        final int packetId = this.getPacketId(packet.getClass());
        writer.writeVarInt(packetId);
        writer.writeString(this.gson.toJson((Object)packet));
        return writer.toByteArray();
    }
}
