// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketUpdateCosmetics extends Packet
{
    private String json;
    
    public PacketUpdateCosmetics() {
        this.json = null;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        final boolean hasJsonString = buf.readBoolean();
        if (hasJsonString) {
            this.json = buf.readString();
        }
    }
    
    @Override
    public void write(final PacketBuffer buf) {
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getJson() {
        return this.json;
    }
}
