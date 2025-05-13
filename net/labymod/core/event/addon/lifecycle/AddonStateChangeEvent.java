// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.addon.lifecycle;

import net.labymod.api.event.Event;

public class AddonStateChangeEvent implements Event
{
    private final String namespace;
    private final State previousState;
    private final State newState;
    
    public AddonStateChangeEvent(final String namespace, final Boolean previousState, final Boolean newState) {
        this.namespace = namespace;
        this.previousState = State.of(previousState);
        this.newState = State.of(newState);
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public State previousState() {
        return this.previousState;
    }
    
    public State state() {
        return this.newState;
    }
    
    public enum State
    {
        UNKNOWN, 
        ENABLED, 
        DISABLED;
        
        public static State of(final Boolean value) {
            if (value == null) {
                return State.UNKNOWN;
            }
            return value ? State.ENABLED : State.DISABLED;
        }
    }
}
