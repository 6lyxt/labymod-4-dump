// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.Event;

public class FieldOfViewTickEvent implements Event
{
    private final float originalFov;
    private final float originalOldFov;
    private final int tick;
    private float fov;
    private float oldFov;
    private float modifier;
    private boolean overwriteVanilla;
    
    public FieldOfViewTickEvent(final float fov, final float oldFov, final float modifier, final int tick) {
        this.overwriteVanilla = false;
        this.tick = tick;
        this.fov = fov;
        this.oldFov = oldFov;
        this.modifier = modifier;
        this.originalFov = fov;
        this.originalOldFov = oldFov;
    }
    
    public float getFov() {
        return this.fov;
    }
    
    public void setFov(final float fov) {
        this.fov = fov;
    }
    
    public float getOldFov() {
        return this.oldFov;
    }
    
    public void setOldFov(final float oldFov) {
        this.oldFov = oldFov;
    }
    
    public float getModifier() {
        return this.modifier;
    }
    
    public void setModifier(final float modifier) {
        this.modifier = modifier;
    }
    
    public boolean isOverwriteVanilla() {
        return this.overwriteVanilla;
    }
    
    public void setOverwriteVanilla(final boolean overwriteVanilla) {
        this.overwriteVanilla = overwriteVanilla;
    }
    
    public float getOriginalFov() {
        return this.originalFov;
    }
    
    public float getOriginalOldFov() {
        return this.originalOldFov;
    }
    
    public int getTick() {
        return this.tick;
    }
}
