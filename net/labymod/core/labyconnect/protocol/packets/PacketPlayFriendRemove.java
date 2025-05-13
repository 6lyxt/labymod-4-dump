// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayFriendRemove extends Packet
{
    private DefaultFriend toRemove;
    
    public PacketPlayFriendRemove(final DefaultFriend toRemove) {
        this.toRemove = toRemove;
    }
    
    public PacketPlayFriendRemove() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.toRemove = buf.readChatUser();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.toRemove);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public Friend getToRemove() {
        return this.toRemove;
    }
}
