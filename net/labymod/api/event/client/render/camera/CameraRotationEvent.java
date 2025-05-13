// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.camera;

import net.labymod.api.event.Event;

public class CameraRotationEvent implements Event
{
    private float yaw;
    private float pitch;
    
    public CameraRotationEvent(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
}
