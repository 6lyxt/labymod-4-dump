// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectOutgoingFriendRequestAddBulkEvent;
import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import java.util.Collection;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;
import java.util.UUID;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import java.util.List;
import net.labymod.core.labyconnect.DefaultLabyConnect;

public class OutgoingFriendRequestsMessageListener implements MessageListener
{
    private final DefaultLabyConnect labyConnect;
    private final List<OutgoingFriendRequest> requests;
    
    public OutgoingFriendRequestsMessageListener(final DefaultLabyConnect labyConnect, final List<OutgoingFriendRequest> requests) {
        this.labyConnect = labyConnect;
        this.requests = requests;
    }
    
    @Override
    public void listen(final String message) {
        final JsonArray array = (JsonArray)OutgoingFriendRequestsMessageListener.GSON.fromJson(message, (Class)JsonArray.class);
        final List<OutgoingFriendRequest> requests = new ArrayList<OutgoingFriendRequest>();
        for (int i = 0; i < array.size(); ++i) {
            final JsonObject request = array.get(i).getAsJsonObject();
            final String name = request.get("name").getAsString();
            final UUID uuid = UUID.fromString(request.get("uuid").getAsString());
            requests.add(new DefaultOutgoingFriendRequest(uuid, name));
        }
        this.requests.addAll(requests);
        if (requests.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddEvent(this.labyConnect, requests.get(0)));
        }
        else {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddBulkEvent(this.labyConnect, new ArrayList<OutgoingFriendRequest>(requests)));
        }
    }
}
