// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.core.client.gfx.buffer.DefaultBlaze3DBufferSource;

public class VersionedBlaze3DBufferSource extends DefaultBlaze3DBufferSource
{
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        eag bufferSource = (eag)this.getBufferSource();
        if (this.temporaryBuffer != null) {
            bufferSource = (eag)this.temporaryBuffer;
        }
        final dfq buffer = bufferSource.getBuffer((eao)renderType);
        if (buffer instanceof final BufferConsumer bufferConsumer) {
            return bufferConsumer;
        }
        return new FallbackVertexConsumer(buffer);
    }
    
    @Override
    public void endBuffer() {
        super.endBuffer();
        this.getBufferSource().a();
    }
    
    private eag.a getBufferSource() {
        return djz.C().aE().b();
    }
}
