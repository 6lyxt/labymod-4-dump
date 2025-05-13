// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.util.Buffers;
import java.util.Arrays;
import java.nio.FloatBuffer;
import net.labymod.api.util.math.vector.FloatVector3;

public class UniformFloatVector3Array extends AbstractUniform
{
    private final FloatVector3[] components;
    private final FloatBuffer buffer;
    
    public UniformFloatVector3Array(final String name, final int size) {
        super(name);
        Arrays.fill(this.components = new FloatVector3[size], new FloatVector3());
        this.buffer = Buffers.createFloatBuffer(size * 3);
    }
    
    public void update(final int index, final FloatVector3 other) {
        final FloatVector3 component = this.components[index];
        if (component == null) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        component.set(other);
        this.components[index] = component;
        final int pos = index * 3;
        this.buffer.put(pos, component.getX());
        this.buffer.put(pos + 1, component.getY());
        this.buffer.put(pos + 2, component.getZ());
        this.shouldUpload = true;
    }
    
    @Override
    protected void updateUniform() {
        this.bridge.uniform3fv(this.getLocation(), this.buffer);
    }
}
