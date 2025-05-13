// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.core.client.gfx.buffer.DefaultBlaze3DBufferSource;

public class VersionedBlaze3DBufferSource extends DefaultBlaze3DBufferSource
{
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        foe bufferSource = (foe)this.getBufferSource();
        if (this.temporaryBuffer != null) {
            bufferSource = (foe)this.temporaryBuffer;
        }
        final elt buffer = bufferSource.getBuffer((fom)renderType);
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
    
    private foe.a getBufferSource() {
        return eqv.O().aO().b();
    }
}
