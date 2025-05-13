// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

@Deprecated
public class NetworkDisconnectEvent implements Event
{
    private final Phase phase;
    
    public NetworkDisconnectEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public Phase phase() {
        return this.phase;
    }
}
