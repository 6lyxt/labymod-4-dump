// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.camera;

import net.labymod.api.event.Event;

public class CameraEyeHeightEvent implements Event
{
    private final float partialTicks;
    private float eyeHeight;
    
    public CameraEyeHeightEvent(final float partialTicks, final float eyeHeight) {
        this.partialTicks = partialTicks;
        this.eyeHeight = eyeHeight;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public float getEyeHeight() {
        return this.eyeHeight;
    }
    
    public void setEyeHeight(final float eyeHeight) {
        this.eyeHeight = eyeHeight;
    }
}
