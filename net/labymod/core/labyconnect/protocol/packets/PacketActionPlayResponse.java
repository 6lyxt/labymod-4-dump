// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketActionPlayResponse extends Packet
{
    private short requestId;
    private boolean allowed;
    private String reason;
    
    public PacketActionPlayResponse() {
    }
    
    public PacketActionPlayResponse(final boolean allowed) {
        this.allowed = allowed;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.requestId = buf.readShort();
        if (!(this.allowed = buf.readBoolean())) {
            this.reason = buf.readString();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeShort(this.requestId);
        buf.writeBoolean(this.allowed);
        if (!this.allowed) {
            buf.writeString(this.reason);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public boolean isAllowed() {
        return this.allowed;
    }
}
