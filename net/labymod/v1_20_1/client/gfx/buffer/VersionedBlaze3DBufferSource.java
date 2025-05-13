// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.core.client.gfx.buffer.DefaultBlaze3DBufferSource;

public class VersionedBlaze3DBufferSource extends DefaultBlaze3DBufferSource
{
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        fjx bufferSource = this.getBufferSource();
        if (this.temporaryBuffer != null) {
            bufferSource = (fjx)this.temporaryBuffer;
        }
        final ein buffer = bufferSource.getBuffer((fkf)renderType);
        if (buffer instanceof final BufferConsumer bufferConsumer) {
            return bufferConsumer;
        }
        return new FallbackVertexConsumer(buffer);
    }
    
    @Override
    public void endBuffer() {
        final fjx multiBufferSource = this.getBufferSource();
        if (multiBufferSource instanceof final fjx.a bufferSource) {
            bufferSource.b();
        }
    }
    
    public fjx getBufferSource() {
        return (fjx)enn.N().aN().b();
    }
}
