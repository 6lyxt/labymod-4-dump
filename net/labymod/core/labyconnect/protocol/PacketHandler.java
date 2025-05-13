// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol;

import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class PacketHandler extends SimpleChannelInboundHandler<Object>
{
    protected void channelRead0(final ChannelHandlerContext ctx, final Object packet) {
        this.handlePacket((Packet)packet);
    }
    
    protected void handlePacket(final Packet packet) {
        packet.handle(this);
    }
    
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
    
    public abstract void handle(final PacketHelloPong p0);
    
    public abstract void handle(final PacketPlayPlayerOnline p0);
    
    public abstract void handle(final PacketLoginComplete p0);
    
    public abstract void handle(final PacketKick p0);
    
    public abstract void handle(final PacketDisconnect p0);
    
    public abstract void handle(final PacketLoginFriend p0);
    
    public abstract void handle(final PacketLoginRequest p0);
    
    public abstract void handle(final PacketNotAllowed p0);
    
    public abstract void handle(final PacketPing p0);
    
    public abstract void handle(final PacketServerMessage p0);
    
    public abstract void handle(final PacketMessage p0);
    
    public abstract void handle(final PacketPlayTyping p0);
    
    public abstract void handle(final PacketPlayRequestAddFriendResponse p0);
    
    public abstract void handle(final PacketPlayRequestRemove p0);
    
    public abstract void handle(final PacketPlayFriendRemove p0);
    
    public abstract void handle(final PacketPlayFriendStatus p0);
    
    public abstract void handle(final PacketPlayFriendPlayingOn p0);
    
    public abstract void handle(final PacketPlayInviteLanWorld p0);
    
    public abstract void handle(final PacketPlayAcceptLanWorldInvite p0);
    
    public abstract void handle(final PacketPlayRejectLanWorldInvite p0);
    
    public abstract void handle(final PacketIceCredentials p0);
    
    public abstract void handle(final PacketLoginTime p0);
    
    public abstract void handle(final PacketEncryptionRequest p0);
    
    public abstract void handle(final PacketMojangStatus p0);
    
    public abstract void handle(final PacketUpdateCosmetics p0);
    
    public abstract void handle(final PacketAddonMessage p0);
    
    public abstract void handle(final PacketUserBadge p0);
    
    public abstract void handle(final PacketActionPlayResponse p0);
    
    public abstract void handle(final PacketActionRequestResponse p0);
    
    public abstract void handle(final PacketAddonDevelopment p0);
    
    public abstract void handle(final PacketActionBroadcast p0);
    
    public abstract void handle(final PacketPlayBroadcastPayload p0);
}
