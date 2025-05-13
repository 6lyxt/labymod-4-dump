// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gfx.shader.material.KawaseShaderMaterial;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.shader.material.PositionTextureColorRoundShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.pipeline.program.ShaderCompileMode;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateDrawPhase;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.BlurRenderer;

@Singleton
@Implements(BlurRenderer.class)
public class DefaultBlurRenderer implements BlurRenderer
{
    private static final FloatMatrix4 DEFAULT_MODEL_VIEW_MATRIX;
    private static final int MAX_RENDER_TARGETS = 4;
    private static final int MAX_WIDTH = 1280;
    private static final int MAX_HEIGHT = 720;
    private final FloatMatrix4 projectionMatrix;
    private final RenderTarget[] renderTargets;
    private final Minecraft minecraft;
    private final GFXBridge gfx;
    private final GFXRenderPipeline renderPipeline;
    private final BorderRadius borderRadius;
    
    @Inject
    public DefaultBlurRenderer(final EventBus eventBus, final GFXRenderPipeline renderPipeline) {
        this.projectionMatrix = FloatMatrix4.newIdentity();
        this.renderTargets = new RenderTarget[4];
        this.borderRadius = new BorderRadius();
        this.renderPipeline = renderPipeline;
        this.gfx = renderPipeline.gfx();
        for (int index = 0; index < this.renderTargets.length; ++index) {
            final RenderTarget renderTarget = new RenderTarget();
            renderTarget.addColorAttachment(0, TextureFilter.LINEAR);
            this.renderTargets[index] = renderTarget;
        }
        this.minecraft = Laby.labyAPI().minecraft();
        this.resizeRenderTargets();
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        this.resizeRenderTargets();
    }
    
    @Override
    public void renderRectangle(@NotNull final Stack stack, @NotNull final Rectangle rectangle, final int radius) {
        final ThemeService themeService = Laby.references().themeService();
        final FancyThemeConfig config = themeService.getThemeConfig(FancyThemeConfig.class);
        if (config == null) {
            return;
        }
        final HighQuality quality = config.blurQuality().get();
        if (quality == HighQuality.NONE) {
            return;
        }
        this.gfx.glPushDebugGroup(0, () -> "Kawase Blur Pass");
        int bindingFramebuffer = this.gfx.getBindingFramebuffer();
        if (ImmediateDrawPhase.RENDER_TARGET_RENDER_PASS && bindingFramebuffer == 0) {
            bindingFramebuffer = this.minecraft.mainTarget().getId();
        }
        RenderTarget currentRenderTarget;
        final RenderTarget mainTarget = currentRenderTarget = this.minecraft.mainTarget();
        if (bindingFramebuffer != currentRenderTarget.getId()) {
            currentRenderTarget = this.renderPipeline.getActivityRenderTarget();
        }
        this.renderPipeline.matrixStorage().setModelViewMatrix(DefaultBlurRenderer.DEFAULT_MODEL_VIEW_MATRIX, 4);
        this.gfx.setActiveTexture(0);
        this.gfx.bindTexture2D(TextureId.of(mainTarget.findColorAttachment().getId()));
        float offset = radius * 0.5f;
        if (quality == HighQuality.MEDIUM) {
            offset *= 0.5f;
        }
        else if (quality == HighQuality.LOW) {
            offset *= 0.25f;
        }
        final int size = this.renderTargets.length;
        final boolean scissorActive = this.gfx.isScissorActive();
        if (scissorActive) {
            this.gfx.disableScissor();
        }
        this.processKawaseBlurDown(size, offset);
        this.processKawaseBlurUp(size, offset);
        if (scissorActive) {
            this.gfx.enableScissor();
        }
        final RenderTarget renderTarget = this.renderTargets[0];
        this.gfx.setActiveTexture(0);
        this.gfx.bindTexture2D(TextureId.of(renderTarget.findColorAttachment().getId()));
        this.gfx.viewport(0, 0, currentRenderTarget.getWidth(), currentRenderTarget.getHeight());
        this.gfx.bindFramebuffer(FramebufferTarget.BOTH, bindingFramebuffer);
        this.renderBlur(stack, rectangle);
        this.gfx.glPopDebugGroup();
    }
    
    @Override
    public void setEdgeRadius(final BorderRadius borderRadius) {
        if (borderRadius == null) {
            return;
        }
        this.borderRadius.set(borderRadius);
    }
    
    private void processKawaseBlurDown(final int size, final float offset) {
        for (int index = 0; index < size; ++index) {
            final int finalIndex = index;
            this.gfx.glPushDebugGroup(finalIndex + 1, () -> "Kawase DOWN Blur Pass " + finalIndex);
            this.render(this.renderTargets[finalIndex], BlurAlgorithm.KAWASE_DOWN, offset);
            this.gfx.glPopDebugGroup();
        }
    }
    
    private void processKawaseBlurUp(final int size, final float offset) {
        for (int index = size - 1; index >= 0; --index) {
            final int finalIndex = index;
            this.gfx.glPushDebugGroup(finalIndex + 1, () -> "Kawase UP Blur Pass " + finalIndex);
            this.render(this.renderTargets[finalIndex], BlurAlgorithm.KAWASE_UP, offset);
            this.gfx.glPopDebugGroup();
        }
    }
    
    private void renderBlur(final Stack stack, final Rectangle rectangle) {
        final BufferBuilder bufferBuilder = this.renderPipeline.getDefaultBufferBuilder();
        final RenderProgram renderProgram = RenderPrograms.getPositionTextureColor(ShaderCompileMode.ROUND_RECTANGLE_POST_PROCESSING);
        bufferBuilder.begin(renderProgram);
        final ShaderMaterial shaderMaterial = renderProgram.shaderMaterial();
        if (shaderMaterial instanceof final PositionTextureColorRoundShaderMaterial material) {
            this.configureShaderMaterial(material, rectangle);
        }
        final float left = rectangle.getLeft();
        final float top = rectangle.getTop();
        final float right = rectangle.getRight();
        final float bottom = rectangle.getBottom();
        final float alpha = Laby.references().renderPipeline().getAlpha();
        final int argb = ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, alpha);
        bufferBuilder.pos(left, top, 0.0f).uv(0.0f, 0.0f).color(argb).endVertex();
        bufferBuilder.pos(left, bottom, 0.0f).uv(0.0f, 1.0f).color(argb).endVertex();
        bufferBuilder.pos(right, bottom, 0.0f).uv(1.0f, 1.0f).color(argb).endVertex();
        bufferBuilder.pos(right, top, 0.0f).uv(1.0f, 0.0f).color(argb).endVertex();
        this.renderPipeline.setProjectionMatrix();
        this.renderPipeline.setModelViewMatrix(stack.getProvider().getPosition());
        this.gfx.storeBlaze3DStates();
        this.gfx.enableBlend();
        this.gfx.defaultBlend();
        ImmediateRenderer.drawWithProgram(bufferBuilder.end());
        this.gfx.restoreBlaze3DStates();
        this.reset(renderProgram.shaderMaterial());
        stack.translate(0.0f, 0.0f, 1.0f);
    }
    
    private void configureShaderMaterial(final PositionTextureColorRoundShaderMaterial material, final Rectangle rectangle) {
        final float left = rectangle.getLeft();
        final float top = rectangle.getTop();
        final float right = rectangle.getRight();
        final float bottom = rectangle.getBottom();
        material.vertices().set(left, top, right, bottom);
        material.size().set(right - left, bottom - top);
        material.radius().set(this.borderRadius.getRightBottom(), this.borderRadius.getRightTop(), this.borderRadius.getLeftBottom(), this.borderRadius.getLeftTop());
        material.lowerEdgeSoftness().set(this.borderRadius.getLowerEdgeSoftness());
        material.upperEdgeSoftness().set(this.borderRadius.getUpperEdgeSoftness());
    }
    
    private void render(final RenderTarget renderTarget, final BlurAlgorithm algorithm, final float offset) {
        final int width = renderTarget.getWidth();
        final int height = renderTarget.getHeight();
        this.projectionMatrix.setOrthographic((float)width, (float)(-height), 1000.0f, 3000.0f);
        this.renderPipeline.setProjectionMatrix(this.projectionMatrix);
        final RenderProgram renderProgram = RenderPrograms.getBlurProgram(algorithm);
        final ShaderMaterial material = renderProgram.shaderMaterial();
        if (material instanceof final KawaseShaderMaterial kawaseShaderMaterial) {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            kawaseShaderMaterial.size().set((float)window.getRawWidth(), (float)window.getRawHeight());
            kawaseShaderMaterial.offset().set(offset, offset);
        }
        final BufferBuilder bufferBuilder = Laby.references().gfxRenderPipeline().getDefaultBufferBuilder();
        bufferBuilder.begin(renderProgram);
        bufferBuilder.pos(0.0f, (float)height, 0.0f).uv(0.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos((float)width, (float)height, 0.0f).uv(1.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos((float)width, 0.0f, 0.0f).uv(1.0f, 1.0f).color(-1).endVertex();
        bufferBuilder.pos(0.0f, 0.0f, 0.0f).uv(0.0f, 1.0f).color(-1).endVertex();
        renderTarget.bindWrite(true);
        ImmediateRenderer.drawWithProgram(bufferBuilder.end());
        this.gfx.setActiveTexture(0);
        this.gfx.bindTexture2D(TextureId.of(renderTarget.findColorAttachment().getId()));
        renderTarget.unbindWrite();
    }
    
    private void reset(final ShaderMaterial shaderMaterial) {
        this.borderRadius.set(BorderRadius.EMPTY);
        if (shaderMaterial instanceof final PositionTextureColorRoundShaderMaterial material) {
            material.vertices().set(0.0f, 0.0f, 0.0f, 0.0f);
            material.size().set(0.0f, 0.0f);
            material.radius().set(0.0f, 0.0f, 0.0f, 0.0f);
        }
    }
    
    private void resizeRenderTargets() {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.resizeRenderTargets(window.getRawWidth(), window.getRawHeight());
    }
    
    private void resizeRenderTargets(int width, int height) {
        if (width > 1280) {
            width = 1280;
        }
        if (height > 720) {
            height = 720;
        }
        for (int index = 0; index < this.renderTargets.length; ++index) {
            if (index == 0) {
                this.renderTargets[index].resize(width, height);
            }
            else {
                final int downSample = index + 1;
                final RenderTarget renderTarget = this.renderTargets[index];
                renderTarget.resize(width / downSample, height / downSample);
            }
        }
    }
    
    public RenderTarget[] getRenderTargets() {
        return this.renderTargets;
    }
    
    static {
        DEFAULT_MODEL_VIEW_MATRIX = FloatMatrix4.createTranslateMatrix(0.0f, 0.0f, -2000.0f);
    }
}
