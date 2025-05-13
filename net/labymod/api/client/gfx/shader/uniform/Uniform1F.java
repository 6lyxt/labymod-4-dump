// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform1F extends UniformFloat
{
    public Uniform1F(final String name) {
        super(name, 1);
    }
    
    public final void set(final float v0) {
        this.update(0, v0);
    }
    
    public void updateUniform() {
        this.bridge.uniform1fv(this.getLocation(), this.buffer);
    }
}
