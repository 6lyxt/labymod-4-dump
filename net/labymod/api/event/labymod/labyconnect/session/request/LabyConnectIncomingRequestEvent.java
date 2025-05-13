// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectIncomingRequestEvent extends LabyConnectEvent
{
    private final IncomingFriendRequest request;
    
    protected LabyConnectIncomingRequestEvent(final LabyConnect api, final IncomingFriendRequest request) {
        super(api);
        this.request = request;
    }
    
    public IncomingFriendRequest request() {
        return this.request;
    }
}
