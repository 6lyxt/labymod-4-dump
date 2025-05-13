// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.math.vector.FloatVector3;

public class Uniform3F extends UniformFloat
{
    public Uniform3F(final String name) {
        super(name, 3);
    }
    
    public final void set(final float v0, final float v1, final float v2) {
        this.update(0, v0);
        this.update(1, v1);
        this.update(2, v2);
    }
    
    public final void set(final FloatVector3 value) {
        this.set(value.getX(), value.getY(), value.getZ());
    }
    
    public void updateUniform() {
        this.bridge.uniform3fv(this.getLocation(), this.buffer);
    }
}
