// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketChatVisibilityChange extends Packet
{
    private boolean visible;
    
    @Override
    public void read(final PacketBuffer buf) {
        this.visible = buf.readBoolean();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeBoolean(this.visible);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public boolean isVisible() {
        return this.visible;
    }
}
