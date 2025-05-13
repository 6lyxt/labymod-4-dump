// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayRequestRemove extends Packet
{
    private String playerName;
    
    public PacketPlayRequestRemove(final String playerName) {
        this.playerName = playerName;
    }
    
    public PacketPlayRequestRemove() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.playerName = buf.readString();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeString(this.playerName);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
}
