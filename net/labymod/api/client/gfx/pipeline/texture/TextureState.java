// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture;

public class TextureState
{
    private int previousBinding;
    private int binding;
    private boolean previousEnabled;
    private boolean enabled;
    
    public void setBinding(final int binding) {
        this.previousBinding = this.binding;
        this.binding = binding;
    }
    
    public void enable() {
        this.previousEnabled = this.enabled;
        this.enabled = true;
    }
    
    public void disable() {
        this.previousEnabled = this.enabled;
        this.enabled = false;
    }
    
    public boolean changed() {
        return this.binding != this.previousBinding || this.enabled != this.previousEnabled;
    }
}
