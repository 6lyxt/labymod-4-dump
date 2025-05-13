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

public class PacketPlayTyping extends Packet
{
    private DefaultFriend player;
    private DefaultFriend inChatWith;
    private boolean typing;
    
    public PacketPlayTyping(final DefaultFriend player, final DefaultFriend inChatWith, final boolean typing) {
        this.player = player;
        this.inChatWith = inChatWith;
        this.typing = typing;
    }
    
    public PacketPlayTyping() {
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.inChatWith = buf.readChatUser();
        this.typing = buf.readBoolean();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeUser(this.inChatWith);
        buf.writeBoolean(this.typing);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
        packetHandler.handle(this);
    }
    
    public Friend getInChatWith() {
        return this.inChatWith;
    }
    
    public Friend getPlayer() {
        return this.player;
    }
    
    public boolean isTyping() {
        return this.typing;
    }
}
