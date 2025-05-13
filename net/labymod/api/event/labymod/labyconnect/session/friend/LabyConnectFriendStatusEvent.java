// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.UserStatus;

public class LabyConnectFriendStatusEvent extends LabyConnectFriendEvent
{
    private final UserStatus previousStatus;
    private final UserStatus status;
    
    public LabyConnectFriendStatusEvent(final LabyConnect api, final Friend friend, final UserStatus previousStatus, final UserStatus status) {
        super(api, friend);
        this.previousStatus = previousStatus;
        this.status = status;
    }
    
    public UserStatus getPreviousStatus() {
        return this.previousStatus;
    }
    
    public UserStatus getStatus() {
        return this.status;
    }
    
    public boolean isOnline() {
        return this.status != UserStatus.OFFLINE;
    }
    
    public boolean wasOnline() {
        return this.previousStatus != UserStatus.OFFLINE;
    }
}
