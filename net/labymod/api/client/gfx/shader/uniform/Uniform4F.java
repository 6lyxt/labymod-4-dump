// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.math.vector.FloatVector4;

public class Uniform4F extends UniformFloat
{
    public Uniform4F(final String name) {
        super(name, 4);
    }
    
    public final void set(final FloatVector4 value) {
        this.set(value.getX(), value.getY(), value.getZ(), value.getW());
    }
    
    public final void set(final float v0, final float v1, final float v2, final float v3) {
        this.update(0, v0);
        this.update(1, v1);
        this.update(2, v2);
        this.update(3, v3);
    }
    
    public void updateUniform() {
        this.bridge.uniform4fv(this.getLocation(), this.buffer);
    }
}
