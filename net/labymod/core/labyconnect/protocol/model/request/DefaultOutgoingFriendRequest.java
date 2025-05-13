// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.model.request;

import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.Laby;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import java.util.UUID;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

public class DefaultOutgoingFriendRequest extends DefaultFriend implements OutgoingFriendRequest
{
    public DefaultOutgoingFriendRequest(final UUID uuid, final String username) {
        super(uuid, username, UserStatus.OFFLINE, "", null, 0, TimeUtil.getCurrentTimeMillis(), 0L, "", 0L, 0L, 0, false);
    }
    
    @Override
    public void withdraw() {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            session.declineFriendRequest(this.uniqueId);
        }
    }
}
