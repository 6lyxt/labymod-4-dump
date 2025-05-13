// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayRequestAddFriend extends Packet
{
    private String name;
    
    public PacketPlayRequestAddFriend(final String name) {
        this.name = name;
    }
    
    public PacketPlayRequestAddFriend() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        final byte[] a = new byte[buf.readInt()];
        for (int i = 0; i < a.length; ++i) {
            a[i] = buf.readByte();
        }
        this.name = new String(a);
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeInt(this.name.getBytes().length);
        buf.writeBytes(this.name.getBytes());
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public String getName() {
        return this.name;
    }
}
