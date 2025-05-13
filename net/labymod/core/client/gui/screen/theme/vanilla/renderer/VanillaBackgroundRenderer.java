// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.Border;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaBackgroundRenderer extends ThemeRenderer<AbstractWidget<?>>
{
    public VanillaBackgroundRenderer() {
        super("Background");
    }
    
    public VanillaBackgroundRenderer(final String name) {
        super(name);
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final Rectangle rectangle = widget.bounds().rectangle(BoundsType.MIDDLE);
        final Rectangle border = widget.bounds().rectangle(BoundsType.BORDER);
        this.renderBackground(context.stack(), widget, rectangle, border, context.getTickDelta());
    }
    
    public void renderBackground(final Stack stack, final AbstractWidget<?> widget, final Rectangle rectangle, final Rectangle border, final float delta) {
        this.renderBackground(stack, widget, rectangle, border, ColorUtil.lerpedColor(widget.backgroundColorTransitionDuration().get(), delta, widget.backgroundColor()));
    }
    
    public void renderBackground(final Stack stack, final AbstractWidget<?> widget, final Rectangle rectangle, final Rectangle border, final int backgroundColor) {
        if (widget.backgroundImage != null) {
            widget.backgroundImage.render(stack, rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight(), false, (backgroundColor == 0) ? -1 : backgroundColor);
        }
        else {
            final BorderRadius borderRadius = widget.getBorderRadius();
            if (borderRadius != null && borderRadius.isSet()) {
                final int borderColor = (widget.border == null) ? 0 : widget.border.getTopColor();
                this.rectangleRenderer.pos(rectangle).rounded(borderRadius.getLeftTop(), borderRadius.getRightTop(), borderRadius.getLeftBottom(), borderRadius.getRightBottom()).color(backgroundColor).borderColor(borderColor).borderSoftness(borderRadius.getBorderSoftness()).borderThickness(borderRadius.getThickness()).lowerEdgeSoftness(borderRadius.getLowerEdgeSoftness()).upperEdgeSoftness(borderRadius.getUpperEdgeSoftness()).render(stack);
            }
            else if (backgroundColor != 0) {
                final boolean floating = widget.useFloatingPointPosition().get();
                final float left = MathHelper.applyFloatingPointPosition(floating, rectangle.getLeft());
                final float top = MathHelper.applyFloatingPointPosition(floating, rectangle.getTop());
                final float right = MathHelper.applyFloatingPointPosition(floating, rectangle.getRight());
                final float bottom = MathHelper.applyFloatingPointPosition(floating, rectangle.getBottom());
                this.rectangleRenderer.pos(left, top, right, bottom).color(backgroundColor).render(stack);
            }
        }
        if (widget.hasBorder()) {
            this.renderBorder(stack, widget, rectangle, border);
        }
    }
    
    public void renderBorder(final Stack stack, final AbstractWidget<?> widget, final Rectangle rectangle, final Rectangle borderRect) {
        final boolean floating = widget.useFloatingPointPosition().get();
        final float leftBorder = MathHelper.applyFloatingPointPosition(floating, borderRect.getLeft());
        final float topBorder = MathHelper.applyFloatingPointPosition(floating, borderRect.getTop());
        final float rightBorder = MathHelper.applyFloatingPointPosition(floating, borderRect.getRight());
        final float bottomBorder = MathHelper.applyFloatingPointPosition(floating, borderRect.getBottom());
        final float leftMiddle = MathHelper.applyFloatingPointPosition(floating, rectangle.getLeft());
        final float topMiddle = MathHelper.applyFloatingPointPosition(floating, rectangle.getTop());
        final float rightMiddle = MathHelper.applyFloatingPointPosition(floating, rectangle.getRight());
        final float bottomMiddle = MathHelper.applyFloatingPointPosition(floating, rectangle.getBottom());
        final BatchRectangleRenderer renderer = this.rectangleRenderer.beginBatch(stack);
        final Border border = widget.border;
        float leftOffset = 0.0f;
        float rightOffset = 0.0f;
        if (border.getLeftWidth() != 0.0f) {
            leftOffset = leftMiddle - leftBorder;
            renderer.pos(leftBorder, topBorder, leftMiddle, bottomBorder).color(border.getLeftColor()).build();
        }
        if (border.getRightWidth() != 0.0f) {
            rightOffset = rightBorder - rightMiddle;
            renderer.pos(rightMiddle, topBorder, rightBorder, bottomBorder).color(border.getRightColor()).build();
        }
        if (border.getTopWidth() != 0.0f) {
            renderer.pos(leftBorder + leftOffset, topBorder, rightBorder - rightOffset, topMiddle).color(border.getTopColor()).build();
        }
        if (border.getBottomWidth() != 0.0f) {
            renderer.pos(leftBorder + leftOffset, bottomMiddle, rightBorder - rightOffset, bottomBorder).color(border.getBottomColor()).build();
        }
        renderer.upload();
    }
}
