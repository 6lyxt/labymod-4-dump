// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol;

import java.util.Iterator;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatusUpdate;
import net.labymod.core.labyconnect.protocol.packets.PacketChatVisibilityChange;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketPong;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlay;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayChangeOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayDenyFriendRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginVersion;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginData;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginStart;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPing;
import java.util.HashMap;
import java.util.Map;

public class Protocol
{
    private final Map<Integer, Class<? extends Packet>> packets;
    
    public Protocol() {
        this.packets = new HashMap<Integer, Class<? extends Packet>>();
        this.register(0, PacketHelloPing.class);
        this.register(1, PacketHelloPong.class);
        this.register(2, PacketLoginStart.class);
        this.register(3, PacketLoginData.class);
        this.register(4, PacketLoginFriend.class);
        this.register(5, PacketLoginRequest.class);
        this.register(6, PacketLoginOptions.class);
        this.register(7, PacketLoginComplete.class);
        this.register(8, PacketLoginTime.class);
        this.register(9, PacketLoginVersion.class);
        this.register(10, PacketEncryptionRequest.class);
        this.register(11, PacketEncryptionResponse.class);
        this.register(14, PacketPlayPlayerOnline.class);
        this.register(16, PacketPlayRequestAddFriend.class);
        this.register(17, PacketPlayRequestAddFriendResponse.class);
        this.register(18, PacketPlayRequestRemove.class);
        this.register(19, PacketPlayDenyFriendRequest.class);
        this.register(20, PacketPlayFriendRemove.class);
        this.register(21, PacketPlayChangeOptions.class);
        this.register(22, PacketPlayServerStatus.class);
        this.register(23, PacketPlayFriendStatus.class);
        this.register(24, PacketPlayFriendPlayingOn.class);
        this.register(25, PacketPlayTyping.class);
        this.register(26, PacketMojangStatus.class);
        this.register(27, PacketActionPlay.class);
        this.register(28, PacketActionPlayResponse.class);
        this.register(29, PacketActionRequest.class);
        this.register(30, PacketActionRequestResponse.class);
        this.register(31, PacketUpdateCosmetics.class);
        this.register(32, PacketAddonMessage.class);
        this.register(33, PacketUserBadge.class);
        this.register(34, PacketAddonDevelopment.class);
        this.register(35, PacketPlayInviteLanWorld.class);
        this.register(36, PacketPlayAcceptLanWorldInvite.class);
        this.register(37, PacketPlayRejectLanWorldInvite.class);
        this.register(38, PacketIceCredentials.class);
        this.register(60, PacketDisconnect.class);
        this.register(61, PacketKick.class);
        this.register(62, PacketPing.class);
        this.register(63, PacketPong.class);
        this.register(64, PacketServerMessage.class);
        this.register(65, PacketMessage.class);
        this.register(66, PacketNotAllowed.class);
        this.register(67, PacketChatVisibilityChange.class);
        this.register(68, PacketPlayServerStatusUpdate.class);
        this.register(69, PacketUserTracker.class);
        this.register(70, PacketActionBroadcast.class);
        this.register(71, PacketPlayBroadcastPayload.class);
    }
    
    private void register(final int id, final Class<?> clazz) {
        try {
            this.packets.put(id, (Class<? extends Packet>)clazz);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public Packet getPacket(final int id) throws Exception {
        if (!this.packets.containsKey(id)) {
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        }
        return this.packets.get(id).getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
    }
    
    public int getPacketId(final Packet packet) {
        for (final Map.Entry<Integer, Class<? extends Packet>> entry : this.packets.entrySet()) {
            final Class<? extends Packet> clazz = entry.getValue();
            if (clazz.isInstance(packet)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Packet " + String.valueOf(packet) + " is not registered.");
    }
    
    public Map<Integer, Class<? extends Packet>> getPackets() {
        return this.packets;
    }
}
