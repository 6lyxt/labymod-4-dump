// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.entity;

import net.labymod.api.event.Event;

public class EntityRenderPassEvent implements Event
{
    private final Phase phase;
    
    public EntityRenderPassEvent(final Phase phase) {
        this.phase = phase;
    }
    
    public Phase getPhase() {
        return this.phase;
    }
    
    public enum Phase
    {
        BEFORE, 
        AFTER;
    }
}
