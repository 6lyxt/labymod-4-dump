// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;

public class DefaultBlaze3DBufferSource implements Blaze3DBufferSource
{
    protected Object temporaryBuffer;
    
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        throw new UnsupportedOperationException("Implement me!");
    }
    
    @Override
    public void endBuffer() {
    }
    
    @Override
    public void setTemporaryBuffer(final Object bufferSource) {
        this.temporaryBuffer = bufferSource;
    }
    
    @Override
    public void resetTemporaryBuffer() {
        this.temporaryBuffer = null;
    }
}
