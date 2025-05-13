// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import java.io.IOException;
import net.labymod.core.labyconnect.util.GZIPCompression;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayBroadcastPayload extends Packet
{
    private PacketUserTracker.EnumTrackingChannel channel;
    private UUID sender;
    private String key;
    private byte[] compressedJson;
    
    public PacketPlayBroadcastPayload(final PacketUserTracker.EnumTrackingChannel channel, final String key, final String json) {
        this.channel = channel;
        this.sender = new UUID(0L, 0L);
        this.key = key;
        this.setJson(json);
    }
    
    public PacketPlayBroadcastPayload() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.channel = PacketUserTracker.EnumTrackingChannel.values()[buf.readByte()];
        this.sender = new UUID(buf.readLong(), buf.readLong());
        this.key = buf.readString();
        final byte[] data = new byte[buf.readInt()];
        buf.readBytes(data);
        this.compressedJson = data;
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeByte(this.channel.ordinal());
        buf.writeLong(this.sender.getMostSignificantBits());
        buf.writeLong(this.sender.getLeastSignificantBits());
        buf.writeString(this.key);
        buf.writeInt(this.compressedJson.length);
        buf.writeBytes(this.compressedJson);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public PacketUserTracker.EnumTrackingChannel getChannel() {
        return this.channel;
    }
    
    public UUID getSender() {
        return this.sender;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getJson() {
        try {
            return GZIPCompression.decompress(this.compressedJson);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] getCompressedJson() {
        return this.compressedJson;
    }
    
    public void setJson(final String json) {
        try {
            this.compressedJson = GZIPCompression.compress(json);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (this.compressedJson.length > 256) {
            throw new IllegalArgumentException("Payload is too big! " + this.compressedJson.length + " > 256");
        }
    }
}
