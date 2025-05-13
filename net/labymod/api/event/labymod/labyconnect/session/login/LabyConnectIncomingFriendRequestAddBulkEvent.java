// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.login;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectIncomingFriendRequestAddBulkEvent extends LabyConnectEvent
{
    private final List<IncomingFriendRequest> requests;
    
    public LabyConnectIncomingFriendRequestAddBulkEvent(final LabyConnect api, final List<IncomingFriendRequest> requests) {
        super(api);
        this.requests = requests;
    }
    
    public List<IncomingFriendRequest> getRequests() {
        return this.requests;
    }
}
