// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.renderer.retained;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.target.RenderTarget;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;
import net.labymod.api.client.gfx.pipeline.program.DynamicRenderProgram;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.Laby;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.util.function.Function;
import net.labymod.core.main.user.shop.item.ItemRendererContext;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;

public class DynamicRenderableItem extends RenderableItem
{
    private boolean renderInWorld;
    
    public void setRenderedBuffer(final RenderedBuffer renderedBuffer) {
        this.renderedBuffer = renderedBuffer;
        final RenderProgram renderProgram = this.renderedBuffer.renderProgram();
        final ShaderProgram shaderProgram = renderProgram.shaderProgram();
        final UniformBone boneMatrices = shaderProgram.getUniform("BoneMatrices");
        if (boneMatrices != null) {
            this.boneMatrices = boneMatrices.read(this.boneMatrices);
        }
    }
    
    public void setModel(final Model model) {
        this.model = model;
    }
    
    public void setItemRenderEnvironment(final ItemRendererContext context, final Function<FloatMatrix4, FloatMatrix4> modelViewMatrixFunction, final Function<FloatMatrix4, FloatMatrix4> projectionMatrixFunction) {
        this.modelViewMatrix = modelViewMatrixFunction.apply(context.modelViewMatrix());
        this.projectionMatrix = projectionMatrixFunction.apply(context.projectionMatrix());
        this.packedLightCoords = context.getPackedLightCoords();
        this.renderInWorld = context.isRenderInWorld();
    }
    
    @Override
    public void render() {
        if (this.renderedBuffer == null) {
            return;
        }
        final RenderTarget target = RenderUtil.bindMainTarget();
        final RenderProgram renderProgram = this.renderedBuffer.renderProgram();
        final ShaderProgram shaderProgram = renderProgram.shaderProgram();
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        final GFXBridge gfx = renderPipeline.gfx();
        int options = 1;
        if (this.renderInWorld) {
            options |= 0x4;
        }
        renderPipeline.matrixStorage().setModelViewMatrix(this.modelViewMatrix, options);
        this.modelViewMatrix = renderPipeline.getModelViewMatrix();
        gfx.bindRenderProgram(renderProgram);
        final UniformMatrix4 modelViewMatrix = shaderProgram.getUniform("ModelViewMatrix");
        if (modelViewMatrix != null) {
            modelViewMatrix.setAndUpload(this.modelViewMatrix);
        }
        final UniformMatrix4 projectionMatrix = shaderProgram.getUniform("ProjectionMatrix");
        if (projectionMatrix != null) {
            projectionMatrix.setAndUpload(this.projectionMatrix);
        }
        final Uniform1I environmentContext = shaderProgram.getUniform("EnvironmentContext");
        if (environmentContext != null) {
            environmentContext.set(renderPipeline.renderEnvironmentContext().isScreenContext());
            environmentContext.upload();
        }
        final Uniform2I lightCoords = shaderProgram.getUniform("LightCoords");
        if (lightCoords != null) {
            lightCoords.set(this.packedLightCoords & 0xFFFF, this.packedLightCoords >> 16 & 0xFFFF);
            lightCoords.upload();
        }
        final UniformBone boneMatrices = shaderProgram.getUniform("BoneMatrices");
        if (boneMatrices != null) {
            boneMatrices.store(this.boneMatrices);
            boneMatrices.upload();
        }
        gfx.blaze3DGlStatePipeline().shaderUniformPipeline().light().setupShaderLights(shaderProgram);
        if (renderProgram instanceof DynamicRenderProgram) {
            final ResourceLocation location = this.model.getTextureLocation();
            DynamicRenderableItem.DEFAULT_TEXTURING_RENDER_PARAMETER.setLocation(location);
            ((DynamicRenderProgram)renderProgram).applyDynamicParameter(DynamicRenderableItem.DEFAULT_TEXTURING_RENDER_PARAMETER);
        }
        final RenderedBuffer renderedBuffer = this.renderedBuffer;
        final RenderedBuffer renderedBuffer2 = this.renderedBuffer;
        Objects.requireNonNull(renderedBuffer2);
        renderedBuffer.useVertexArray(renderedBuffer2::draw);
        gfx.unbindRenderProgram();
        if (target != null) {
            target.unbind();
        }
    }
}
