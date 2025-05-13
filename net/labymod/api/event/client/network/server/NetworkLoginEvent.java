// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Event;

@Deprecated
public class NetworkLoginEvent implements Event
{
    private final ServerData serverData;
    
    public NetworkLoginEvent(final ServerData serverData) {
        this.serverData = serverData;
    }
    
    public ServerData serverData() {
        return this.serverData;
    }
}
