// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ModelUploader
{
    public static final ModelVertexWriter DEFAULT_WRITER = (consumer, x, y, z, u, v, packedColor, normalX, normalY, normalZ, modelId, glowing, rainbowCycle) -> {
        consumer.putFloat(x, y, z);
        consumer.putFloat(u, v);
        consumer.putInt(packedColor);
        consumer.putByte(MathHelper.normalIntValue(normalX), MathHelper.normalIntValue(normalY), MathHelper.normalIntValue(normalZ));
        consumer.putFloat(modelId);
        consumer.putFloat(glowing ? 1.0f : 0.0f);
        consumer.putFloat((float)rainbowCycle);
    };
    
    ModelUploader model(final Model p0);
    
    ModelUploader modelVertexWriter(final ModelVertexWriter p0);
    
    @Deprecated
    ModelUploader shaderBasedAnimation();
    
    void directUpload();
    
    @Nullable
    BufferState upload(final RenderProgram p0);
    
    @FunctionalInterface
    public interface ModelVertexWriter
    {
        void write(final BufferConsumer p0, final float p1, final float p2, final float p3, final float p4, final float p5, final int p6, final float p7, final float p8, final float p9, final float p10, final boolean p11, final long p12);
    }
}
