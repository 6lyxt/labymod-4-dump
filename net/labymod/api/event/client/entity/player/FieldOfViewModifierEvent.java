// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.Event;

public class FieldOfViewModifierEvent implements Event
{
    private float fieldOfView;
    
    public FieldOfViewModifierEvent(final float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }
    
    public float getFieldOfView() {
        return this.fieldOfView;
    }
    
    public void setFieldOfView(final float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }
}
