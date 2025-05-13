// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.Buffers;
import java.nio.FloatBuffer;

public abstract class UniformFloat extends AbstractUniform
{
    protected FloatBuffer buffer;
    
    protected UniformFloat(final String name, final int size) {
        super(name);
        this.buffer = Buffers.createFloatBuffer(size);
    }
    
    public void update(final int index, final float value) {
        if (this.buffer.get(index) == value) {
            return;
        }
        this.buffer.put(index, value);
        this.shouldUpload = true;
    }
}
