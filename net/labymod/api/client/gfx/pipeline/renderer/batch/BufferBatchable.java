// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.batch;

import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.ResourceRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.SpriteRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.RectangleRenderer;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.labymod.api.client.gfx.pipeline.renderer.Renderer;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;

public abstract class BufferBatchable implements Batchable
{
    private final Object2ReferenceMap<Class<? extends Renderer>, Renderer> renderers;
    private float offset;
    
    public BufferBatchable() {
        this.renderers = (Object2ReferenceMap<Class<? extends Renderer>, Renderer>)new Object2ReferenceOpenHashMap();
        this.registerRenderer(new RectangleRenderer(this));
        this.registerRenderer(new SpriteRenderer(this));
        this.registerRenderer(new ResourceRenderer(this));
    }
    
    @NotNull
    @Override
    public <T extends Renderer> T getRenderer(final Class<T> rendererClass) {
        return Objects.requireNonNull(this.renderers.get((Object)rendererClass));
    }
    
    public abstract BufferBuilder bufferBuilder();
    
    protected void registerRenderer(final Renderer renderer) {
        this.renderers.put((Object)renderer.getClass(), (Object)renderer);
    }
    
    @Override
    public void flush() {
        final BufferBuilder bufferBuilder = this.bufferBuilder();
        if (bufferBuilder == null) {
            return;
        }
        final BufferState bufferState = bufferBuilder.end();
        if (bufferState == null) {
            return;
        }
        ImmediateRenderer.drawWithProgram(bufferState);
    }
    
    @Override
    public void flush(final FloatMatrix4 modelViewMatrix) {
        this.updateMatrices(modelViewMatrix);
        this.flush();
    }
    
    @Override
    public void end(final FloatMatrix4 modelViewMatrix) {
        this.updateMatrices(modelViewMatrix);
        this.end();
    }
    
    private void updateMatrices(final FloatMatrix4 modelViewMatrix) {
        final GFXRenderPipeline pipeline = Laby.references().gfxRenderPipeline();
        pipeline.setProjectionMatrix();
        pipeline.setModelViewMatrix(modelViewMatrix);
    }
    
    @Override
    public float getZOffset() {
        return this.offset;
    }
    
    @Override
    public void setZOffset(final float offset) {
        this.offset = offset;
    }
}
