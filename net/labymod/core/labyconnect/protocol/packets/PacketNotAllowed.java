// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketNotAllowed extends Packet
{
    private String reason;
    private long until;
    
    public PacketNotAllowed(final String reason, final long until) {
        this.reason = reason;
        this.until = until;
    }
    
    public PacketNotAllowed() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.reason = buf.readString();
        this.until = buf.readLong();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.reason);
        buf.writeLong(this.until);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public long getUntil() {
        return this.until;
    }
    
    public String getReason() {
        return this.reason;
    }
}
