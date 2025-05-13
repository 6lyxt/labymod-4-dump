// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnect;

public class LabyConnectFriendPinUpdateEvent extends LabyConnectFriendEvent
{
    public LabyConnectFriendPinUpdateEvent(final LabyConnect api, final Friend friend) {
        super(api, friend);
    }
}
