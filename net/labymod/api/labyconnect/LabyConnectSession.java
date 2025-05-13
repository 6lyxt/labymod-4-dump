// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect;

import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.util.math.Direction;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.client.network.server.ServerData;
import java.util.UUID;

public interface LabyConnectSession
{
    void removeFriend(final UUID p0);
    
    void sendCurrentServer(final ServerData p0, final String p1, final boolean p2);
    
    void sendLeaveCurrentServer();
    
    void sendSettings();
    
    @Nullable
    Friend getFriend(final UUID p0);
    
    @Nullable
    Chat getChat(final UUID p0);
    
    @Nullable
    IncomingFriendRequest getIncomingRequest(final UUID p0);
    
    @Nullable
    OutgoingFriendRequest getOutgoingRequest(final UUID p0);
    
    default int getOnlineFriendCount() {
        int count = 0;
        for (final Friend friend : this.getFriends()) {
            if (friend.isOnline()) {
                ++count;
            }
        }
        return count;
    }
    
    default int getUnreadCount() {
        int count = 0;
        for (final Chat chat : this.getChats()) {
            count += chat.getUnreadCount();
        }
        return count;
    }
    
    List<Friend> getFriends();
    
    List<Chat> getChats();
    
    TokenStorage tokenStorage();
    
    List<IncomingFriendRequest> getIncomingRequests();
    
    void sendFriendRequest(final String p0);
    
    void acceptFriendRequest(final UUID p0);
    
    void declineFriendRequest(final UUID p0);
    
    List<OutgoingFriendRequest> getOutgoingRequests();
    
    void playEmote(final short p0);
    
    void spray(final short p0, final int p1, final double p2, final double p3, final double p4, final Direction p5, final float p6);
    
    User self();
    
    void inviteToLanWorld(final Friend p0);
    
    void acceptLanWorldInvite(final Friend p0);
    
    void rejectLanWorldInvite(final Friend p0);
    
    void retractLanWorldInvite(final Friend p0);
    
    void sendAddonDevelopment(final String p0, final UUID[] p1, final byte[] p2);
    
    void sendChatMessage(final UUID p0, final TextChatMessage p1);
    
    UUID sendChatFile(final UUID p0, final String p1, final byte[] p2);
    
    boolean isAuthenticated();
    
    boolean isPremium();
    
    boolean isConnectionEstablished();
    
    @Deprecated
    void refreshOpenChat(final UUID p0);
    
    void sendBroadcastPayload(@NotNull final String p0, @NotNull final JsonElement p1);
    
    void sendSurroundingBroadcastPayload(@NotNull final String p0, @NotNull final JsonElement p1);
    
    void sendTextureUpdated(final MojangTextureChangedResponse p0);
    
    byte[] getFile(final UUID p0);
}
