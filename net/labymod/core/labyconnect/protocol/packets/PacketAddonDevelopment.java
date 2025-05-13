// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.io.IOException;
import net.labymod.core.labyconnect.util.GZIPCompression;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketAddonDevelopment extends Packet
{
    private UUID sender;
    private UUID[] receivers;
    private String key;
    private byte[] data;
    
    public PacketAddonDevelopment() {
    }
    
    public PacketAddonDevelopment(final UUID sender, final String key, final byte[] data) {
        this.sender = sender;
        this.key = key;
        try {
            this.data = GZIPCompression.compress(new String(data));
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        this.receivers = new UUID[0];
    }
    
    public PacketAddonDevelopment(final UUID sender, final UUID[] receivers, final String key, final byte[] data) {
        this.sender = sender;
        this.receivers = receivers;
        this.key = key;
        try {
            this.data = GZIPCompression.compressBytes(data);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.sender = new UUID(buf.readLong(), buf.readLong());
        final int receiverCnt = buf.readShort();
        this.receivers = new UUID[receiverCnt];
        for (int i = 0; i < this.receivers.length; ++i) {
            this.receivers[i] = new UUID(buf.readLong(), buf.readLong());
        }
        this.key = buf.readString();
        final byte[] data = new byte[buf.readInt()];
        buf.readBytes(data);
        this.data = data;
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeLong(this.sender.getMostSignificantBits());
        buf.writeLong(this.sender.getLeastSignificantBits());
        buf.writeShort((short)this.receivers.length);
        for (final UUID receiver : this.receivers) {
            buf.writeLong(receiver.getMostSignificantBits());
            buf.writeLong(receiver.getLeastSignificantBits());
        }
        buf.writeString(this.key);
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public UUID getSender() {
        return this.sender;
    }
    
    public UUID[] getReceivers() {
        return this.receivers;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public byte[] getData() {
        try {
            return GZIPCompression.decompressBytes(this.data);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
