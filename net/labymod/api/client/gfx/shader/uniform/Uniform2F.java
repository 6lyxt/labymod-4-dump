// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform2F extends UniformFloat
{
    public Uniform2F(final String name) {
        super(name, 2);
    }
    
    public final void set(final float v0, final float v1) {
        this.update(0, v0);
        this.update(1, v1);
    }
    
    public void updateUniform() {
        this.bridge.uniform2fv(this.getLocation(), this.buffer);
    }
}
