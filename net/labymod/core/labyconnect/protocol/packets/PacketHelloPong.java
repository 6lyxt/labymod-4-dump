// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketHelloPong extends Packet
{
    private long timestamp;
    
    public PacketHelloPong() {
    }
    
    public PacketHelloPong(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.timestamp = buf.readLong();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeLong(this.timestamp);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
}
