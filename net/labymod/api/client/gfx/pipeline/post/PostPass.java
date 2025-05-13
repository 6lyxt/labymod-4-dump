// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gui.window.Window;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.HashMap;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.util.function.IntSupplier;
import java.util.Map;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;

public class PostPass
{
    private static final boolean LEGACY_VERSION;
    private final PostProcessor processor;
    private final String name;
    private final PostPassRenderTarget source;
    private final PostPassRenderTarget destination;
    private final RenderProgram renderProgram;
    private final ShaderProgram shaderProgram;
    private final Map<String, IntSupplier> effects;
    private CustomPostPassProcessor customPostPassProcessor;
    private FloatMatrix4 projectionMatrix;
    
    public PostPass(final PostProcessor processor, final String name, final PostPassRenderTarget source, final PostPassRenderTarget destination, final PostPassEffectHolder effect) {
        this.effects = new HashMap<String, IntSupplier>();
        this.processor = processor;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.renderProgram = effect.renderProgram();
        this.shaderProgram = effect.shaderProgram();
    }
    
    public void setProjectionMatrix(final FloatMatrix4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
    
    public void setCustomPostPassProcessor(final CustomPostPassProcessor customPostPassProcessor) {
        this.customPostPassProcessor = customPostPassProcessor;
    }
    
    public void process(final float time) {
        if (this.projectionMatrix == null) {
            this.processor.requestProjectionMatrix();
        }
        this.source.unbindWrite();
        final float width = (float)this.destination.getWidth();
        final float height = (float)this.destination.getHeight();
        Laby.gfx().viewport(0, 0, (int)width, (int)height);
        final ShaderProgram shaderProgram = this.shaderProgram;
        shaderProgram.bind();
        shaderProgram.setSampler("DiffuseSampler", this.source.findColorAttachment().getId());
        for (final Map.Entry<String, IntSupplier> entry : this.effects.entrySet()) {
            shaderProgram.setSampler(entry.getKey(), entry.getValue().getAsInt());
        }
        shaderProgram.setMatrix4x4("ProjectionMatrix", this.projectionMatrix);
        shaderProgram.setVec2("SourceSize", (float)this.source.getWidth(), (float)this.source.getHeight());
        shaderProgram.setVec2("DestinationSize", width, height);
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        shaderProgram.setFloat("Time", time);
        shaderProgram.setVec2("ScreenSize", (float)window.getRawWidth(), (float)window.getRawHeight());
        this.processCustomPostPass(shaderProgram, time);
        shaderProgram.apply();
        this.destination.clearTarget();
        this.destination.bindWrite(false);
        this.drawPass(width, height);
        shaderProgram.clear();
        this.destination.unbindWrite();
        this.source.unbindRead();
    }
    
    protected void processCustomPostPass(final ShaderProgram shaderProgram, final float time) {
        if (this.customPostPassProcessor != null) {
            this.customPostPassProcessor.process(this.name, shaderProgram, time);
        }
    }
    
    public PostProcessor processor() {
        return this.processor;
    }
    
    private void drawPass(final float width, final float height) {
        final BufferBuilder bufferBuilder = Laby.references().gfxRenderPipeline().getDefaultBufferBuilder();
        bufferBuilder.begin(this.renderProgram);
        bufferBuilder.pos(0.0f, 0.0f, 500.0f).uv(0.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos(width, 0.0f, 500.0f).uv(0.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos(width, height, 500.0f).uv(0.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos(0.0f, height, 500.0f).uv(0.0f, 0.0f).color(-1).endVertex();
        if (PostPass.LEGACY_VERSION) {
            final Blaze3DGlStatePipeline blaze3DGlStatePipeline = Laby.gfx().blaze3DGlStatePipeline();
            blaze3DGlStatePipeline.disableBlend();
            blaze3DGlStatePipeline.disableDepthTest();
            blaze3DGlStatePipeline.disableAlpha();
            blaze3DGlStatePipeline.disableLighting();
            blaze3DGlStatePipeline.disableColorMaterial();
            blaze3DGlStatePipeline.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            blaze3DGlStatePipeline.blaze3DFog().disable();
        }
        ImmediateRenderer.drawWithProgram(bufferBuilder.end());
    }
    
    public void addEffect(final String name, final IntSupplier textureIdSupplier) {
        this.effects.put(name, textureIdSupplier);
    }
    
    static {
        LEGACY_VERSION = MinecraftVersions.V1_12_2.orOlder();
    }
}
