// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.state;

import org.lwjgl.opengl.GL11;

public final class BooleanState
{
    private final int target;
    private boolean enabled;
    
    public BooleanState(final int target) {
        this.target = target;
    }
    
    public void enable() {
        this.setEnabled(true);
    }
    
    public void disable() {
        this.setEnabled(false);
    }
    
    private void setEnabled(final boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            GL11.glEnable(this.target);
        }
        else {
            GL11.glDisable(this.target);
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
