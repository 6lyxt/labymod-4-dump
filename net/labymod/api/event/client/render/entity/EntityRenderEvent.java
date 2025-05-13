// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.entity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Phase;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

public class EntityRenderEvent implements Event
{
    private final Entity entity;
    private final Phase phase;
    
    public EntityRenderEvent(final Entity entity, final Phase phase) {
        this.entity = entity;
        this.phase = phase;
    }
    
    @NotNull
    public Entity entity() {
        return this.entity;
    }
    
    public Phase phase() {
        return this.phase;
    }
}
