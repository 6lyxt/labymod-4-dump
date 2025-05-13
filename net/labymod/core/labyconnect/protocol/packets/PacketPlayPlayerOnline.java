// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayPlayerOnline extends Packet
{
    private DefaultFriend newOnlinePlayer;
    
    public PacketPlayPlayerOnline(final DefaultFriend newOnlinePlayer) {
        this.newOnlinePlayer = newOnlinePlayer;
    }
    
    public PacketPlayPlayerOnline() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.newOnlinePlayer = buf.readChatUser();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.newOnlinePlayer);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public DefaultFriend getPlayer() {
        return this.newOnlinePlayer;
    }
}
