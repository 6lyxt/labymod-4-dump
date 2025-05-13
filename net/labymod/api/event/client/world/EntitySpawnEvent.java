// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

public class EntitySpawnEvent implements Event
{
    private final int entityId;
    private final Entity entity;
    
    public EntitySpawnEvent(final int entityId, final Entity entity) {
        this.entityId = entityId;
        this.entity = entity;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public Entity entity() {
        return this.entity;
    }
}
