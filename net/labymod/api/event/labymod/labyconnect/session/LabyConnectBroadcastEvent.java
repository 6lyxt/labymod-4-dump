// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.labyconnect.LabyConnect;
import com.google.gson.JsonElement;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectBroadcastEvent extends LabyConnectEvent
{
    private final Action action;
    private final UUID sender;
    private final String key;
    private final JsonElement payload;
    
    public LabyConnectBroadcastEvent(@NotNull final LabyConnect api, @NotNull final Action action, @NotNull final UUID sender, @NotNull final String key, @NotNull final JsonElement payload) {
        super(api);
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(sender, "Sender cannot be null!");
        Objects.requireNonNull(key, "Key cannot be null!");
        Objects.requireNonNull(payload, "Payload cannot be null!");
        this.action = action;
        this.sender = sender;
        this.key = key;
        this.payload = payload;
    }
    
    @NotNull
    public Action action() {
        return this.action;
    }
    
    @NotNull
    public UUID getSender() {
        return this.sender;
    }
    
    @NotNull
    public String getKey() {
        return this.key;
    }
    
    @NotNull
    public JsonElement getPayload() {
        return this.payload;
    }
    
    public enum Action
    {
        SEND, 
        RECEIVE;
    }
}
