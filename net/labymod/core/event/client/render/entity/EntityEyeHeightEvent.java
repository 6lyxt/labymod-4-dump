// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

public class EntityEyeHeightEvent implements Event
{
    private final Entity entity;
    private float eyeHeight;
    
    public EntityEyeHeightEvent(final Entity entity, final float eyeHeight) {
        this.entity = entity;
        this.eyeHeight = eyeHeight;
    }
    
    public Entity entity() {
        return this.entity;
    }
    
    public float getEyeHeight() {
        return this.eyeHeight;
    }
    
    public void setEyeHeight(final float eyeHeight) {
        this.eyeHeight = eyeHeight;
    }
}
