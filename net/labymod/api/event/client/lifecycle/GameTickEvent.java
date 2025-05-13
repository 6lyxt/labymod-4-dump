// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.lifecycle;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public final class GameTickEvent implements Event
{
    private final Phase phase;
    
    public GameTickEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final GameTickEvent that = (GameTickEvent)object;
        return this.phase == that.phase;
    }
    
    @Override
    public int hashCode() {
        return (this.phase != null) ? this.phase.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "TickEvent[phase=" + String.valueOf(this.phase);
    }
}
