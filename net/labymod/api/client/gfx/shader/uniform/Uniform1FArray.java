// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public class Uniform1FArray extends UniformFloat
{
    public Uniform1FArray(final String name, final int size) {
        super(name, size);
    }
    
    @Override
    protected void updateUniform() {
        this.bridge.uniform1fv(this.getLocation(), this.buffer);
    }
}
