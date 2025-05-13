// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer.background;

import net.labymod.api.client.render.batch.RectangleRenderContext;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.background.VanillaScreenBackgroundRenderer;

public class FancyScreenBackgroundRenderer extends VanillaScreenBackgroundRenderer
{
    private static final float BACKGROUND_ASPECT_RATIO = 1.7777778f;
    private final ResourceLocation backgroundLocation;
    protected final AbstractBootLogoRenderer loadingRenderer;
    
    public FancyScreenBackgroundRenderer(final Theme theme) {
        this.backgroundLocation = theme.resource(theme.getNamespace(), "textures/background.png");
        this.loadingRenderer = LabyMod.references().bootLogoController().renderer();
        this.api.eventBus().registerListener(this);
    }
    
    @Override
    public boolean renderBackground(final Stack stack, final Object screenInstance) {
        return super.renderBackground(stack, screenInstance);
    }
    
    @Override
    public boolean hasScreenDynamicBackground(final Object screenInstance) {
        return !this.isMainMenuActivity(screenInstance);
    }
    
    @Override
    public boolean renderIngameBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final Object screenInstance) {
        final Window window = this.minecraft.minecraftWindow();
        final int windowHeight = MathHelper.ceil(window.getRawHeight() / window.getScale());
        final int scaledHeight = window.getScaledHeight();
        final int padding = windowHeight - scaledHeight;
        final int paddingTop = padding / 2;
        this.api.renderPipeline().rectangleRenderer().setupGradient(stack, renderer -> renderer.renderBackgroundGradient(left, top - paddingTop, right, bottom + padding));
        return true;
    }
    
    @Override
    public boolean renderMenuBackground(final Stack stack, float left, float top, float right, float bottom, final Object screenInstance) {
        final Window window = this.minecraft.minecraftWindow();
        final int windowHeight = MathHelper.ceil(window.getRawHeight() / window.getScale());
        final int scaledHeight = window.getScaledHeight();
        final float offset = windowHeight - (bottom - top);
        top = -offset / 2.0f;
        bottom = windowHeight - offset / 2.0f;
        final int padding = windowHeight - scaledHeight;
        final int paddingTop = padding / 2;
        final float width = right - left;
        final float height = bottom - top;
        final float aspectRatio = width / height;
        if (aspectRatio > 1.7777778f) {
            final float newHeight = width / 1.7777778f;
            top = (height - newHeight) / 2.0f - paddingTop;
            bottom = top + newHeight;
        }
        else {
            final float newWidth = height * 1.7777778f;
            left = (width - newWidth) / 2.0f;
            right = left + newWidth;
        }
        final RenderPipeline renderPipeline = this.api.renderPipeline();
        renderPipeline.setModifiedAlpha(false);
        renderPipeline.resourceRenderer().texture(this.backgroundLocation).pos(left, top, right, bottom).sprite(0.0f, 0.0f, 256.0f, 256.0f).color(1.0f, 1.0f, 1.0f, 1.0f).render(stack);
        renderPipeline.setModifiedAlpha(true);
        return true;
    }
    
    @Nullable
    @Override
    public ResourceLocation getBackgroundLocation() {
        return this.backgroundLocation;
    }
}
