// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import java.util.List;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketLoginFriend extends Packet
{
    private List<DefaultFriend> friends;
    
    public PacketLoginFriend(final List<DefaultFriend> friends) {
        this.friends = friends;
    }
    
    public PacketLoginFriend() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        final List<DefaultFriend> players = new ArrayList<DefaultFriend>();
        for (int a = buf.readInt(), i = 0; i < a; ++i) {
            players.add(buf.readChatUser());
        }
        (this.friends = new ArrayList<DefaultFriend>()).addAll(players);
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeInt(this.getFriends().size());
        for (int i = 0; i < this.getFriends().size(); ++i) {
            final DefaultFriend p = this.getFriends().get(i);
            buf.writeUser(p);
        }
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public List<DefaultFriend> getFriends() {
        return this.friends;
    }
}
