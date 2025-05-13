// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketUserBadge extends Packet
{
    private UUID[] uuids;
    private byte[] ranks;
    
    public PacketUserBadge() {
    }
    
    public PacketUserBadge(final UUID[] uuids) {
        this.uuids = uuids;
    }
    
    public PacketUserBadge(final UUID[] uuids, final byte[] ranks) {
        this.uuids = uuids;
        this.ranks = ranks;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        final int size = buf.readVarIntFromBuffer();
        this.uuids = new UUID[size];
        for (int i = 0; i < size; ++i) {
            this.uuids[i] = new UUID(buf.readLong(), buf.readLong());
        }
        final byte[] bytes = new byte[size];
        buf.readBytes(bytes);
        this.ranks = bytes;
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarIntToBuffer(this.uuids.length);
        for (final UUID uuid : this.uuids) {
            buf.writeLong(uuid.getMostSignificantBits());
            buf.writeLong(uuid.getLeastSignificantBits());
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public byte[] getRanks() {
        return this.ranks;
    }
    
    public UUID[] getUuids() {
        return this.uuids;
    }
}
