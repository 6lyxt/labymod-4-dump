// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.login;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectOutgoingFriendRequestAddBulkEvent extends LabyConnectEvent
{
    private final List<OutgoingFriendRequest> requests;
    
    public LabyConnectOutgoingFriendRequestAddBulkEvent(final LabyConnect api, final List<OutgoingFriendRequest> requests) {
        super(api);
        this.requests = requests;
    }
    
    public List<OutgoingFriendRequest> getRequests() {
        return this.requests;
    }
}
