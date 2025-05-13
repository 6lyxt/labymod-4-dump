// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketKick extends Packet
{
    private String cause;
    
    public PacketKick(final String cause) {
        this.cause = cause;
    }
    
    public PacketKick() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.cause = buf.readString();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.getReason());
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getReason() {
        return this.cause;
    }
}
