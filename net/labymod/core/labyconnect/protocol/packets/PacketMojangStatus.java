// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketMojangStatus extends Packet
{
    @Override
    public void read(final PacketBuffer buf) {
        buf.readInt();
        buf.readString();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
}
