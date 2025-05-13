// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.lifecycle;

import net.labymod.api.event.Event;

public class GameFpsLimitEvent implements Event
{
    private int framerateLimit;
    
    public GameFpsLimitEvent() {
        this.framerateLimit = -1;
    }
    
    public int getFramerateLimit() {
        return this.framerateLimit;
    }
    
    public void setFramerateLimit(final int framerateLimit) {
        this.framerateLimit = framerateLimit;
    }
    
    @Deprecated
    public int framerateLimit() {
        return this.getFramerateLimit();
    }
    
    @Deprecated
    public void framerateLimit(final int framerateLimit) {
        this.setFramerateLimit(framerateLimit);
    }
}
