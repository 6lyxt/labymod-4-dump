// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform4I extends UniformInt
{
    public Uniform4I(final String name) {
        super(name, 4);
    }
    
    public final void set(final int v0, final int v1, final int v2, final int v3) {
        this.update(0, v0);
        this.update(1, v1);
        this.update(2, v2);
        this.update(3, v3);
    }
    
    public void updateUniform() {
        this.bridge.uniform4iv(this.getLocation(), this.buffer);
    }
}
