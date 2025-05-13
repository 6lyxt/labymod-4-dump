// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.buffer;

import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.program.VersionedRenderProgram;
import net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.program.VersionedBlaze3DRenderType;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;

public class VersionedBlaze3DBufferSource implements Blaze3DBufferSource
{
    private final WrappedBufferBuilder bufferBuilder;
    private Blaze3DRenderType renderType;
    
    public VersionedBlaze3DBufferSource() {
        this.bufferBuilder = new WrappedBufferBuilder(2097152);
    }
    
    @Override
    public BufferConsumer getBuffer(final Blaze3DRenderType renderType) {
        this.renderType = renderType;
        if (!this.bufferBuilder.building()) {
            if (!(renderType instanceof VersionedBlaze3DRenderType)) {
                throw new IllegalStateException();
            }
            final VersionedBlaze3DRenderType blaze3DRenderType = (VersionedBlaze3DRenderType)renderType;
            final RenderProgram renderProgram = blaze3DRenderType.renderProgram();
            this.bufferBuilder.begin(renderProgram.mode(), ((VersionedRenderProgram)renderProgram).getVertexFormat());
        }
        return this.bufferBuilder;
    }
    
    @Override
    public void endBuffer() {
        if (this.renderType != null) {
            this.renderType.draw(this.bufferBuilder, 0, 0, 0);
        }
    }
    
    @Override
    public void endLegacyBuffer() {
        if (this.renderType != null) {
            this.renderType.draw(this.bufferBuilder, 0, 0, 0);
            this.getBuffer(this.renderType);
        }
    }
}
