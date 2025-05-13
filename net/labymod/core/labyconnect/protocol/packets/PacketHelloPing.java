// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketHelloPing extends Packet
{
    private long timestamp;
    
    public PacketHelloPing() {
    }
    
    public PacketHelloPing(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.timestamp = buf.readLong();
        buf.readInt();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeLong(this.timestamp);
        buf.writeInt(29);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
}
