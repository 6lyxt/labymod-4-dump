// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.Event;

class ServerEvent implements Event
{
    private final ConnectableServerData serverData;
    
    protected ServerEvent(@NotNull final ConnectableServerData serverData) {
        Objects.requireNonNull(serverData, "Server data cannot be null!");
        this.serverData = serverData;
    }
    
    @NotNull
    public ConnectableServerData serverData() {
        return this.serverData;
    }
}
