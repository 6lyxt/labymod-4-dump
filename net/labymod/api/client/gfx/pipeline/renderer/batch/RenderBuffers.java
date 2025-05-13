// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.batch;

public final class RenderBuffers
{
    private final Batchable singleBatchable;
    
    public RenderBuffers() {
        this.singleBatchable = new SingleBufferBatch();
    }
    
    public Batchable singleBatchable() {
        return this.singleBatchable;
    }
    
    public void close() {
        if (this.singleBatchable != null) {
            this.singleBatchable.close();
        }
    }
}
