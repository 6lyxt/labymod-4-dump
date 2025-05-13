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

public class PacketPlayFriendPlayingOn extends Packet
{
    private DefaultFriend player;
    private String gameModeName;
    
    public PacketPlayFriendPlayingOn(final DefaultFriend player, final String gameModeName) {
        this.player = player;
        this.gameModeName = gameModeName;
    }
    
    public PacketPlayFriendPlayingOn() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.gameModeName = buf.readString();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeString(this.gameModeName);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public String getGameModeName() {
        return this.gameModeName;
    }
    
    public Friend getPlayer() {
        return this.player;
    }
}
