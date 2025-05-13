// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketPlayFriendStatus extends Packet
{
    private DefaultFriend player;
    private DefaultServerInfo playerInfo;
    
    public PacketPlayFriendStatus(final DefaultFriend player, final DefaultServerInfo playerInfo) {
        this.player = player;
        this.playerInfo = playerInfo;
    }
    
    public PacketPlayFriendStatus() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.playerInfo = buf.readServerInfo();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeServerInfo(this.playerInfo);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public Friend getPlayer() {
        return this.player;
    }
    
    public DefaultServerInfo getPlayerInfo() {
        return this.playerInfo;
    }
}
