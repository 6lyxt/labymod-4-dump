// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;
import java.util.UUID;
import com.google.gson.JsonObject;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import java.util.List;
import net.labymod.core.labyconnect.DefaultLabyConnect;

public class FriendRequestResponseMessageListener implements MessageListener
{
    private final DefaultLabyConnect labyConnect;
    private final List<OutgoingFriendRequest> requests;
    
    public FriendRequestResponseMessageListener(final DefaultLabyConnect labyConnect, final List<OutgoingFriendRequest> requests) {
        this.labyConnect = labyConnect;
        this.requests = requests;
    }
    
    @Override
    public void listen(final String message) {
        final JsonObject obj = (JsonObject)FriendRequestResponseMessageListener.GSON.fromJson(message, (Class)JsonObject.class);
        final String name = obj.get("name").getAsString();
        final UUID uuid = UUID.fromString(obj.get("uuid").getAsString());
        final OutgoingFriendRequest request = new DefaultOutgoingFriendRequest(uuid, name);
        this.requests.add(request);
        this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddResponseEvent(this.labyConnect, request));
        this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddEvent(this.labyConnect, request));
    }
}
