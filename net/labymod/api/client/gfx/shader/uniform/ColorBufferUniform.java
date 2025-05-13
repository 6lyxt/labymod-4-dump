// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.Buffers;
import java.nio.FloatBuffer;

public class ColorBufferUniform extends AbstractUniform
{
    private final FloatBuffer buffer;
    
    public ColorBufferUniform(final String name, final int size) {
        super(name);
        this.buffer = Buffers.createFloatBuffer(4 * size);
        for (int index = 0; index < size; ++index) {
            this.set(index, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void set(int position, final float red, final float green, final float blue, final float alpha) {
        position *= 4;
        this.buffer.put(position, red);
        this.buffer.put(position + 1, green);
        this.buffer.put(position + 2, blue);
        this.buffer.put(position + 3, alpha);
        this.shouldUpload = true;
    }
    
    @Override
    protected void updateUniform() {
        this.buffer.rewind();
        this.bridge.uniform4fv(this.getLocation(), this.buffer);
    }
}
