// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class LabyModRefreshEvent implements Event
{
    private final Phase phase;
    
    public LabyModRefreshEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public Phase phase() {
        return this.phase;
    }
}
