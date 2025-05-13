// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.program;

import java.util.Objects;
import net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.buffer.BufferBuilderUploader;
import net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.buffer.WrappedBufferBuilder;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;

public class VersionedBlaze3DRenderType implements Blaze3DRenderType
{
    private final String name;
    private final RenderProgram renderProgram;
    
    public VersionedBlaze3DRenderType(final RenderProgram renderProgram) {
        this.name = renderProgram.getName();
        this.renderProgram = renderProgram;
    }
    
    @Override
    public void apply() {
        Laby.gfx().bindRenderProgram(this.renderProgram);
    }
    
    @Override
    public void clear() {
        Laby.gfx().unbindRenderProgram();
    }
    
    @Override
    public void draw(final BufferConsumer consumer, final int cameraX, final int cameraY, final int cameraZ) {
        if (!(consumer instanceof WrappedBufferBuilder)) {
            return;
        }
        final WrappedBufferBuilder builder = (WrappedBufferBuilder)consumer;
        this.apply();
        BufferBuilderUploader.upload(builder);
        this.clear();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final VersionedBlaze3DRenderType that = (VersionedBlaze3DRenderType)o;
        return Objects.equals(this.renderProgram, that.renderProgram);
    }
    
    @Override
    public int hashCode() {
        return (this.renderProgram != null) ? this.renderProgram.hashCode() : 0;
    }
    
    public RenderProgram renderProgram() {
        return this.renderProgram;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
