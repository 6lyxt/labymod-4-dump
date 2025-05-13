// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform1I extends UniformInt
{
    public Uniform1I(final String name) {
        super(name, 1);
    }
    
    public final void set(final int v0) {
        this.update(0, v0);
    }
    
    public final void set(final boolean condition) {
        this.set(condition ? 1 : 0);
    }
    
    public void updateUniform() {
        this.bridge.uniform1iv(this.getLocation(), this.buffer);
    }
}
