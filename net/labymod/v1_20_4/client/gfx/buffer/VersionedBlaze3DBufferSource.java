// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.core.client.gfx.buffer.DefaultBlaze3DBufferSource;

public class VersionedBlaze3DBufferSource extends DefaultBlaze3DBufferSource
{
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        fth bufferSource = (fth)this.getBufferSource();
        if (this.temporaryBuffer != null) {
            bufferSource = (fth)this.temporaryBuffer;
        }
        final eqf buffer = bufferSource.getBuffer((ftp)renderType);
        if (buffer instanceof final BufferConsumer bufferConsumer) {
            return bufferConsumer;
        }
        return new FallbackVertexConsumer(buffer);
    }
    
    @Override
    public void endBuffer() {
        super.endBuffer();
        this.getBufferSource().b();
    }
    
    private fth.a getBufferSource() {
        return evi.O().aO().c();
    }
}
