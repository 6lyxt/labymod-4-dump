// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectFriendEvent extends LabyConnectEvent
{
    private final Friend friend;
    
    protected LabyConnectFriendEvent(final LabyConnect api, final Friend friend) {
        super(api);
        this.friend = friend;
    }
    
    public Friend friend() {
        return this.friend;
    }
}
