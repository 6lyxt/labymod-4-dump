// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.lifecycle;

import net.labymod.api.event.Event;

public class GameInitializeEvent implements Event
{
    private final Lifecycle lifecycle;
    
    public GameInitializeEvent(final Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }
    
    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }
    
    public enum Lifecycle
    {
        PRE_GAME_STARTED, 
        RESOURCE_INITIALIZATION, 
        POST_STARTUP, 
        POST_GAME_STARTED;
    }
}
