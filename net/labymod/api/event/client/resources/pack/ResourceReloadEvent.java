// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.resources.pack;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class ResourceReloadEvent implements Event
{
    private final Type type;
    private final Phase phase;
    
    public ResourceReloadEvent(final Type type, final Phase phase) {
        this.type = type;
        this.phase = phase;
    }
    
    public Type type() {
        return this.type;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public enum Type
    {
        RELOAD, 
        INITIALIZATION_RESOURCE_PACKS;
    }
}
