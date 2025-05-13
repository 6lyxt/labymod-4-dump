// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session.request;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectOutgoingFriendRequestAddResponseEvent extends LabyConnectEvent
{
    private final String query;
    private final OutgoingFriendRequest request;
    private final String reason;
    
    public LabyConnectOutgoingFriendRequestAddResponseEvent(@NotNull final LabyConnect api, final OutgoingFriendRequest request) {
        super(api);
        this.query = request.getName();
        this.request = request;
        this.reason = null;
    }
    
    public LabyConnectOutgoingFriendRequestAddResponseEvent(@NotNull final LabyConnect api, @NotNull final String query, @Nullable final String reason) {
        super(api);
        this.query = query;
        this.request = null;
        this.reason = reason;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public OutgoingFriendRequest getRequest() {
        if (!this.wasSent()) {
            throw new IllegalStateException("The request was not sent, there is no request.");
        }
        return this.request;
    }
    
    public boolean wasSent() {
        return this.request != null;
    }
    
    public String getReason() {
        if (this.wasSent()) {
            throw new IllegalStateException("The request was sent, there is no reason.");
        }
        return this.reason;
    }
}
