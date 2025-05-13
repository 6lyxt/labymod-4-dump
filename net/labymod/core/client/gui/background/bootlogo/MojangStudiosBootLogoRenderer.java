// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.ResourceRenderer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.renderer.batch.Batchable;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.RectangleRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;

public class MojangStudiosBootLogoRenderer extends AbstractBootLogoRenderer
{
    private boolean animated;
    
    @Override
    public void initialize() {
        super.initialize();
    }
    
    @Override
    public void renderBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final float partialTicks) {
        Laby.gfx().clearDepth();
        final int backgroundColor = this.getBackgroundColor();
        final Batchable batchable = Laby.references().gfxRenderPipeline().renderBuffers().singleBatchable();
        batchable.begin(RenderPrograms.getUIRectangleProgram());
        final RectangleRenderer renderer = batchable.getRenderer(RectangleRenderer.class);
        renderer.draw(left, top, right, bottom, backgroundColor);
        batchable.end(stack);
    }
    
    @Override
    public void renderForeground(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta, final boolean uiContext) {
        final float width = right - left;
        final float height = bottom - top;
        final int centerX = (int)(left + width / 2.0f);
        final int centerY = (int)(top + height / 2.0f);
        final double logoHeight = Math.min(width * 0.75, height) * 0.25;
        final double logoWidth = logoHeight * 4.0;
        final int offsetY = (int)(logoHeight / 2.0);
        final int partWidth = (int)(logoWidth / 2.0);
        final int maxFrames = Textures.Splash.MOJANG_STUDIOS_FRAMES.length;
        final int lastFrame = 4 * maxFrames - 1;
        final float timePassed2 = this.timePassed + TimeUtil.convertDeltaToMilliseconds(tickDelta);
        this.timePassed = timePassed2;
        final float timePassed = timePassed2;
        final int frame = (int)Math.min(timePassed / 40.0f, (float)lastFrame);
        final int index = this.animated ? frame : 0;
        final int sprite = index / 4 % Textures.Splash.MOJANG_STUDIOS_FRAMES.length;
        final int offset = index % 4 * 120;
        final float resolutionHeight = (float)(120 * (this.animated ? 4 : 1));
        final ResourceLocation resourceLocation = this.animated ? Textures.Splash.MOJANG_STUDIOS_FRAMES[sprite] : this.labyAPI.minecraft().textures().splashTexture();
        final Batchable batchable = Laby.references().gfxRenderPipeline().renderBuffers().singleBatchable();
        batchable.begin(RenderPrograms.getDefaultTexture(resourceLocation));
        final ResourceRenderer resourceRenderer = batchable.getRenderer(ResourceRenderer.class);
        resourceRenderer.draw((float)(centerX - partWidth), (float)(centerY - offsetY), (float)partWidth, (float)(int)logoHeight, uiContext ? -0.0625f : 0.0f, (float)offset, 120.0f, 60.0f, 120.0f, resolutionHeight);
        resourceRenderer.draw((float)centerX, (float)(centerY - offsetY), (float)partWidth, (float)(int)logoHeight, 0.0f, (float)(60 + offset), 120.0f, 60.0f, 120.0f, resolutionHeight);
        this.labyAPI.gfxRenderPipeline().setModelViewMatrix(stack.getProvider().getPosition());
        batchable.end();
        final float progressWidth = (float)(logoHeight * 4.0);
        final float progressHeight = 10.0f;
        if (this.progressVisible) {
            final float fadeAt = 0.95f;
            final int alpha = (this.progress >= fadeAt) ? ((int)(255.0f * (1.0f - this.progress) / (1.0f - fadeAt))) : 255;
            final int progressColor = ColorFormat.ARGB32.pack(255, 255, 255, alpha);
            final int backgroundColor = this.getBackgroundColor();
            final float leftBar = centerX - progressWidth / 2.0f;
            final float topBar = top + (bottom - top) * 0.8325f - progressHeight / 2.0f;
            batchable.begin(RenderPrograms.getUIRectangleProgram());
            final RectangleRenderer renderer = batchable.getRenderer(RectangleRenderer.class);
            renderer.draw(leftBar, topBar, leftBar + progressWidth, topBar + progressHeight, ColorFormat.ARGB32.pack(255, 255, 255, alpha));
            renderer.draw(leftBar + 1.0f, topBar + 1.0f, leftBar + 1.0f + progressWidth - 2.0f, topBar + 1.0f + progressHeight - 2.0f, backgroundColor);
            renderer.draw(leftBar + 2.0f, topBar + 2.0f, leftBar + 2.0f + (progressWidth - 4.0f) * this.progress, topBar + 2.0f + progressHeight - 4.0f, progressColor);
            this.labyAPI.gfxRenderPipeline().setModelViewMatrix(stack.getProvider().getPosition());
            batchable.end();
        }
        final float labyModHeight = (float)(logoHeight / 4.0);
        final float labyModWidth = labyModHeight * 756.0f / 164.0f;
        final int logoType = this.labyAPI.config().appearance().darkLoadingScreen().get() ? 164 : 0;
        batchable.begin(RenderPrograms.getDefaultTexture(Textures.Splash.LABYMOD));
        resourceRenderer.draw(centerX - labyModWidth / 2.0f, (float)(centerY - logoHeight - labyModHeight), labyModWidth, labyModHeight, 0.0f, (float)logoType, 756.0f, 164.0f, 756.0f, 492.0f);
        this.labyAPI.gfxRenderPipeline().setModelViewMatrix(stack.getProvider().getPosition());
        batchable.end();
    }
    
    private int getBackgroundColor() {
        final boolean darkLoadingScreen = this.labyAPI.config().appearance().darkLoadingScreen().get();
        return darkLoadingScreen ? ColorFormat.ARGB32.pack(0, 0, 0, 255) : ColorFormat.ARGB32.pack(239, 50, 61, 255);
    }
    
    @Override
    protected void onPreloadResources() {
        if (this.animated) {
            for (final ResourceLocation location : Textures.Splash.MOJANG_STUDIOS_FRAMES) {
                this.preloadTexture(location);
            }
        }
        this.preloadTexture(Textures.Splash.LABYMOD);
    }
}
