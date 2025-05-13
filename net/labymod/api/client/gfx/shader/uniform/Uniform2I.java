// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform2I extends UniformInt
{
    public Uniform2I(final String name) {
        super(name, 2);
    }
    
    public final void set(final int v0, final int v1) {
        this.update(0, v0);
        this.update(1, v1);
    }
    
    public void updateUniform() {
        this.bridge.uniform2iv(this.getLocation(), this.buffer);
    }
}
