// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.core.client.gfx.buffer.DefaultBlaze3DBufferSource;

public class VersionedBlaze3DBufferSource extends DefaultBlaze3DBufferSource
{
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        gdq bufferSource = (gdq)this.getBufferSource();
        if (this.temporaryBuffer != null) {
            bufferSource = (gdq)this.temporaryBuffer;
        }
        final fae buffer = bufferSource.getBuffer((gdy)renderType);
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
    
    private gdq.a getBufferSource() {
        return ffh.Q().aQ().c();
    }
}
