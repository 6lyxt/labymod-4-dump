// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Event;

@Deprecated
public class NetworkSwitchEvent implements Event
{
    @Nullable
    private final ServerData previousServerData;
    @Nullable
    private final ServerData newServerData;
    
    public NetworkSwitchEvent(@Nullable final ServerData previousServerData, @Nullable final ServerData newServerData) {
        this.previousServerData = previousServerData;
        this.newServerData = newServerData;
    }
    
    @Nullable
    public ServerData getPreviousServerData() {
        return this.previousServerData;
    }
    
    @Nullable
    public ServerData getNewServerData() {
        return this.newServerData;
    }
}
