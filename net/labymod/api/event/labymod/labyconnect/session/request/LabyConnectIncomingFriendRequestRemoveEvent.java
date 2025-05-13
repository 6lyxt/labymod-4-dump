// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.labyconnect.LabyConnect;

public class LabyConnectIncomingFriendRequestRemoveEvent extends LabyConnectIncomingRequestEvent
{
    public LabyConnectIncomingFriendRequestRemoveEvent(final LabyConnect api, final IncomingFriendRequest request) {
        super(api, request);
    }
}
