// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.batch;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;

public class SingleBufferBatch extends BufferBatchable
{
    private final BufferBuilder bufferBuilder;
    private RenderProgram currentRenderProgram;
    
    public SingleBufferBatch() {
        this.bufferBuilder = Laby.labyAPI().gfxRenderPipeline().createBufferBuilder(256);
    }
    
    @Override
    public BufferBuilder bufferBuilder() {
        return this.bufferBuilder;
    }
    
    @Override
    public void begin(final RenderProgram renderProgram) {
        this.currentRenderProgram = renderProgram;
        this.bufferBuilder.begin(renderProgram, () -> "Single Buffer Batch");
    }
    
    @Override
    public void changeRenderProgram(final RenderProgram renderProgram) {
        if (this.currentRenderProgram == renderProgram) {
            return;
        }
        this.flush();
        this.bufferBuilder.begin(renderProgram, () -> "Single Buffer Batch");
        this.currentRenderProgram = renderProgram;
    }
    
    @Override
    public void end() {
        this.flush();
    }
    
    @Override
    public void close() {
        if (this.bufferBuilder != null) {
            this.bufferBuilder.close();
        }
    }
}
