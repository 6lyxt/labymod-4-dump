// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer.hudwidget;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetInteractionWidget;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget.VanillaHudWidgetCanvasRenderer;

public class FancyHudWidgetCanvasRenderer extends VanillaHudWidgetCanvasRenderer
{
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        if (DynamicBackgroundController.isEnabled()) {
            final Bounds bounds = widget.bounds();
            final Stack stack = context.stack();
            stack.push();
            this.applyTransformation(stack, widget, TransformationType.STRING);
            this.renderString(stack, bounds);
            stack.pop();
            this.applyTransformation(stack, widget, TransformationType.CANVAS);
            super.renderPre(widget, context);
            this.renderFrame(stack, bounds);
        }
        else {
            super.renderPre(widget, context);
        }
    }
    
    private void applyTransformation(final Stack stack, final AbstractWidget<?> widget, final TransformationType type) {
        final Bounds bounds = widget.bounds();
        final float progress = this.getProgress(widget);
        final float factor = this.getFactor(progress);
        final float distance = bounds.getBottom(BoundsType.INNER);
        stack.translate(bounds.getCenterX(), bounds.getCenterY(), 0.0f);
        stack.translate(0.0f, -distance * factor, 0.0f);
        if (type == TransformationType.CANVAS) {
            stack.rotate(25.0f * factor, 0.0f, 0.0f, 1.0f);
        }
        final float scale = 1.0f - (float)Math.pow(2.718281828459045, -progress);
        stack.scale(scale);
        stack.translate(-bounds.getCenterX(), -bounds.getCenterY(), 0.0f);
    }
    
    private void renderFrame(final Stack stack, final Bounds bounds) {
        final float x = bounds.getX(BoundsType.INNER);
        final float y = bounds.getY(BoundsType.INNER);
        final float width = bounds.getWidth(BoundsType.INNER);
        final float height = bounds.getHeight(BoundsType.INNER);
        final float thickness = 9.5f;
        final ResourceRenderer resourceRenderer = this.labyAPI.renderPipeline().resourceRenderer();
        final BatchResourceRenderer renderer = resourceRenderer.beginBatch(stack, Textures.Hud.FRAME);
        this.labyAPI.renderPipeline().renderContexts().resourceRenderContext().blitNineSliced(x - thickness, y - thickness, width + thickness * 2.0f, height + thickness * 2.0f, 85, 256, 256, 0, 0, -1);
        renderer.upload();
    }
    
    private void renderString(final Stack stack, final Bounds bounds) {
        final float x = bounds.getX(BoundsType.INNER);
        final float y = bounds.getY(BoundsType.INNER);
        final float width = bounds.getWidth(BoundsType.INNER);
        final float height = bounds.getHeight(BoundsType.INNER);
        final float stringSize = 80.0f;
        final ResourceRenderer resourceRenderer = this.labyAPI.renderPipeline().resourceRenderer();
        final BatchResourceRenderer renderer = resourceRenderer.beginBatch(stack, Textures.Hud.STRING);
        this.labyAPI.renderPipeline().renderContexts().resourceRenderContext().blit(x + width / 2.0f - stringSize / 2.0f, y - stringSize * 2.0f, stringSize, stringSize * 2.0f, 0.0f, 0.0f, 128.0f, 256.0f, 128.0f, 256.0f, -1);
        renderer.upload();
    }
    
    private float getProgress(final AbstractWidget<?> widget) {
        final FancyThemeConfig config = this.labyAPI.themeService().getThemeConfig(FancyThemeConfig.class);
        if (config != null && !config.activityTransitions().get()) {
            return 2.1474836E9f;
        }
        long timePassed = TimeUtil.getMillis() - widget.getLastInitialTime();
        if (widget instanceof final HudWidgetInteractionWidget hudWidgetInteractionWidget) {
            timePassed = hudWidgetInteractionWidget.getRenderTimePassed();
        }
        return timePassed / 150.0f;
    }
    
    private float getFactor(final float progress) {
        final float offset = 5.0f;
        return (float)((Math.pow(2.718281828459045, 1.0f - 2.0f * (progress - offset)) / 28.0 - Math.cos(2.0f * (progress - offset)) * Math.pow(2.718281828459045, 1.0f - (progress - offset))) / 10.0) / 20.0f;
    }
    
    private enum TransformationType
    {
        CANVAS, 
        STRING;
    }
}
