// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketLoginComplete extends Packet
{
    private String showDashboardButton;
    
    public PacketLoginComplete(final String string) {
        this.showDashboardButton = string;
    }
    
    public PacketLoginComplete() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.showDashboardButton = buf.readString();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.showDashboardButton);
    }
    
    public int getId() {
        return 2;
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getDashboardPin() {
        return this.showDashboardButton;
    }
}
