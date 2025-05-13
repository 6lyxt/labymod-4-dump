// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectOutgoingRequestEvent extends LabyConnectEvent
{
    private final OutgoingFriendRequest request;
    
    protected LabyConnectOutgoingRequestEvent(final LabyConnect api, final OutgoingFriendRequest request) {
        super(api);
        this.request = request;
    }
    
    public OutgoingFriendRequest request() {
        return this.request;
    }
}
