// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

@ApiStatus.Experimental
public class RenderTypeAttachmentEvent extends DefaultCancellable implements Event
{
    private final String name;
    private final State state;
    private final Phase phase;
    
    public RenderTypeAttachmentEvent(final String name, final State state, final Phase phase) {
        this.name = name;
        this.state = state;
        this.phase = phase;
    }
    
    public String name() {
        return this.name;
    }
    
    public State state() {
        return this.state;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public enum State
    {
        APPLY, 
        CLEAR;
    }
}
