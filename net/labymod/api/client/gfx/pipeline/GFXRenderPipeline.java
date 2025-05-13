// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.gfx.pipeline.renderer.batch.RenderBuffers;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.gfx.target.RenderTarget;
import java.util.function.Consumer;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GFXRenderPipeline
{
    FrameContextRegistry frameContextRegistry();
    
    GFXBridge gfx();
    
    Scissor scissor();
    
    MatrixStorage matrixStorage();
    
    default Matrices matrices() {
        return this.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().matrices();
    }
    
    BufferBuilder getDefaultBufferBuilder();
    
    BufferBuilder createBufferBuilder(final int p0);
    
    default void setProjectionMatrix() {
        this.matrixStorage().setGameProjectionMatrix();
    }
    
    void setProjectionMatrix(final FloatMatrix4 p0);
    
    FloatMatrix4 getProjectionMatrix();
    
    default void setModelViewMatrix(final FloatMatrix4 modelViewMatrix) {
        this.setModelViewMatrix(modelViewMatrix, true);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.21")
    void setModelViewMatrix(final FloatMatrix4 p0, final boolean p1);
    
    FloatMatrix4 getModelViewMatrix();
    
    FloatMatrix4 getViewMatrix();
    
    void setViewMatrix(final FloatMatrix4 p0);
    
    LightmapTexture getLightmapTexture();
    
    RenderEnvironmentContext renderEnvironmentContext();
    
    default void renderToActivityTarget(final Consumer<RenderTarget> renderer) {
        this.renderToTarget(this.getActivityRenderTarget(), renderer);
    }
    
    void renderToTarget(final RenderTarget p0, final Consumer<RenderTarget> p1);
    
    default boolean isActivityRenderTarget() {
        final int bindingFramebuffer = this.gfx().getBindingFramebuffer();
        return this.getActivityRenderTarget().getId() == bindingFramebuffer;
    }
    
    void applyToTarget(final RenderTarget p0, final Consumer<RenderTarget> p1);
    
    RenderTarget getActivityRenderTarget();
    
    void clear(final RenderTarget p0);
    
    OverlappingTranslator overlappingTranslator();
    
    RenderBuffers renderBuffers();
}
