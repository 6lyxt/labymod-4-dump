// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.Buffers;
import java.nio.IntBuffer;

public abstract class UniformInt extends AbstractUniform
{
    protected IntBuffer buffer;
    
    protected UniformInt(final String name, final int size) {
        super(name);
        this.buffer = Buffers.createIntBuffer(size);
    }
    
    public void update(final int index, final int value) {
        if (this.buffer.get(index) == value) {
            return;
        }
        this.buffer.put(index, value);
        this.shouldUpload = true;
    }
}
