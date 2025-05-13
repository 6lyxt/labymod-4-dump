// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayDenyFriendRequest extends Packet
{
    private DefaultUser denied;
    
    public PacketPlayDenyFriendRequest(final DefaultUser denied) {
        this.denied = denied;
    }
    
    public PacketPlayDenyFriendRequest() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.denied = buf.readChatUser();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.denied);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
    
    public <T extends DefaultUser> T getDenied() {
        return (T)this.denied;
    }
}
