// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

public class EntityDestructEvent implements Event
{
    private final Entity entity;
    
    public EntityDestructEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity entity() {
        return this.entity;
    }
}
