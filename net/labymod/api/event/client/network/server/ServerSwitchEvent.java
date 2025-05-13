// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.Event;

public class ServerSwitchEvent implements Event
{
    private final ConnectableServerData previousServerData;
    private final ConnectableServerData newServerData;
    
    public ServerSwitchEvent(@NotNull final ConnectableServerData previousServerData, @NotNull final ConnectableServerData newServerData) {
        Objects.requireNonNull(previousServerData, "Previous server data cannot be null!");
        Objects.requireNonNull(newServerData, "New server data cannot be null!");
        this.previousServerData = previousServerData;
        this.newServerData = newServerData;
    }
    
    @NotNull
    public ConnectableServerData previousServerData() {
        return this.previousServerData;
    }
    
    @NotNull
    public ConnectableServerData newServerData() {
        return this.newServerData;
    }
}
