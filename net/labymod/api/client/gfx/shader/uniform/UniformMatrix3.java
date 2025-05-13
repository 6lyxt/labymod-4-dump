// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.math.vector.FloatMatrix3;
import java.nio.FloatBuffer;

public class UniformMatrix3 extends AbstractUniform
{
    private FloatBuffer floatBuffer;
    private FloatMatrix3 matrix3;
    
    public UniformMatrix3(final String name) {
        super(name);
        this.floatBuffer = Buffers.createFloatBuffer(9);
        (this.matrix3 = new FloatMatrix3()).identity();
        this.matrix3.store(this.floatBuffer);
        this.shouldUpload = true;
    }
    
    public final void set(@NotNull final FloatMatrix3 matrix3) {
        (this.matrix3 = matrix3).store(this.floatBuffer);
        this.shouldUpload = true;
    }
    
    public final void set(@NotNull final FloatBuffer matrixBuffer) {
        this.floatBuffer = matrixBuffer;
        this.shouldUpload = true;
    }
    
    @Override
    protected void updateUniform() {
        this.bridge.uniformMatrix3fv(this.getLocation(), false, this.floatBuffer);
    }
}
