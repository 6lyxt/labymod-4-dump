// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.batch.RenderContext;

public abstract class DefaultRenderContext<T extends RenderContext<T>> implements RenderContext<T>
{
    protected GlStateBridge glStateBridge;
    protected float zOffset;
    protected boolean initialized;
    private BufferBuilder bufferBuilder;
    
    @Override
    public void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        final RenderPipeline renderPipeline = Laby.references().renderPipeline();
        this.bufferBuilder = renderPipeline.createBufferBuilder();
        this.glStateBridge = renderPipeline.glStateBridge();
    }
    
    @Override
    public BufferBuilder getBuilder() {
        this.initialize();
        return this.bufferBuilder;
    }
    
    @Override
    public float zOffset() {
        return this.zOffset;
    }
    
    @Override
    public void zOffset(final float zOffset) {
        this.zOffset = zOffset;
    }
}
