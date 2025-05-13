// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.nio.FloatBuffer;

public class UniformMatrix4 extends AbstractUniform
{
    private FloatBuffer floatBuffer;
    private FloatMatrix4 matrix4;
    
    public UniformMatrix4(final String name) {
        super(name);
        this.floatBuffer = Buffers.createFloatBuffer(16);
        (this.matrix4 = new FloatMatrix4()).identity();
        this.matrix4.store(this.floatBuffer);
        this.shouldUpload = true;
    }
    
    public final void set(@NotNull final FloatMatrix4 matrix4) {
        (this.matrix4 = matrix4).store(this.floatBuffer);
        this.shouldUpload = true;
    }
    
    public final void setAndUpload(@NotNull final FloatMatrix4 matrix) {
        this.set(matrix);
        this.upload();
    }
    
    public final void set(@NotNull final FloatBuffer matrixBuffer) {
        this.floatBuffer = matrixBuffer;
        this.shouldUpload = true;
    }
    
    @Override
    protected void updateUniform() {
        this.bridge.uniformMatrix4fv(this.getLocation(), false, this.floatBuffer);
    }
}
