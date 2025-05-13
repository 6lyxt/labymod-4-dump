// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.labyconnect.LabyConnect;

public class LabyConnectOutgoingFriendRequestRemoveEvent extends LabyConnectOutgoingRequestEvent
{
    public LabyConnectOutgoingFriendRequestRemoveEvent(final LabyConnect api, final OutgoingFriendRequest request) {
        super(api, request);
    }
}
