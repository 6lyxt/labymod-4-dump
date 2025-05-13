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

public class PacketLoginTime extends Packet
{
    private DefaultFriend player;
    private long dateJoined;
    private long lastOnline;
    
    public PacketLoginTime(final DefaultFriend player, final long dateJoined, final long lastOnline) {
        this.player = player;
        this.dateJoined = dateJoined;
        this.lastOnline = lastOnline;
    }
    
    public PacketLoginTime() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.dateJoined = buf.readLong();
        this.lastOnline = buf.readLong();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeLong(this.dateJoined);
        buf.writeLong(this.lastOnline);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public long getDateJoined() {
        return this.dateJoined;
    }
    
    public long getLastOnline() {
        return this.lastOnline;
    }
    
    public Friend getPlayer() {
        return this.player;
    }
}
