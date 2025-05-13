// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.login;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectFriendAddBulkEvent extends LabyConnectEvent
{
    private final List<Friend> friends;
    
    public LabyConnectFriendAddBulkEvent(final LabyConnect api, final List<Friend> friends) {
        super(api);
        this.friends = friends;
    }
    
    public List<Friend> getFriends() {
        return this.friends;
    }
}
