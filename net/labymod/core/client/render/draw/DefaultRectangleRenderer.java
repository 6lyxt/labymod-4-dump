// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.api.util.math.vector.FloatVector4;
import java.util.function.Consumer;
import net.labymod.api.util.ColorUtil;
import java.awt.Color;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder;

@Singleton
@Implements(RectangleRenderer.class)
public class DefaultRectangleRenderer extends DefaultRectangleBuilder<RectangleRenderer> implements RectangleRenderer
{
    private final LabyAPI labyAPI;
    private final DefaultRoundRectangleRenderer roundRectangleRenderer;
    private final RoundedGeometryBuilder roundedGeometryBuilder;
    private final RectangleRenderContext rectangleRenderContext;
    private final BatchRectangleRenderer batchRectangleRenderer;
    
    @Inject
    public DefaultRectangleRenderer(final LabyAPI labyAPI, final DefaultRoundRectangleRenderer roundRectangleRenderer, final RoundedGeometryBuilder roundedGeometryBuilder, final RectangleRenderContext rectangleRenderContext, final BatchRectangleRenderer batchRectangleRenderer) {
        this.labyAPI = labyAPI;
        this.roundRectangleRenderer = roundRectangleRenderer;
        this.roundedGeometryBuilder = roundedGeometryBuilder;
        this.rectangleRenderContext = rectangleRenderContext;
        this.batchRectangleRenderer = batchRectangleRenderer;
    }
    
    @Override
    public BatchRectangleRenderer beginBatch(final Stack stack) {
        return this.batchRectangleRenderer.beginBatch(stack);
    }
    
    @Override
    public BatchRectangleRenderer beginBatch(final Stack stack, final RenderPhase phase) {
        return this.batchRectangleRenderer.beginBatch(stack, phase);
    }
    
    @Override
    public void renderRectangle(final Stack stack, final Rectangle rectangle, final int color) {
        this.pos(rectangle).color(color).render(stack);
    }
    
    @Override
    public void renderRectangle(final Stack stack, final Rectangle rectangle, final Color color) {
        this.pos(rectangle).color(color).render(stack);
    }
    
    @Override
    public void renderOutline(final Stack stack, final float left, final float top, final float right, final float bottom, final int outerColor, final int innerColor, final float thickness) {
        if (ColorUtil.isNoColor(outerColor) && ColorUtil.isNoColor(innerColor)) {
            return;
        }
        this.rectangleRenderContext.begin(stack);
        this.rectangleRenderContext.getBuilder().vertex(stack, left - thickness, bottom + thickness, 0.0f).color(outerColor).next().vertex(stack, left, bottom, 0.0f).color(innerColor).next().vertex(stack, left, top, 0.0f).color(innerColor).next().vertex(stack, left - thickness, top - thickness, 0.0f).color(outerColor).next();
        this.rectangleRenderContext.getBuilder().vertex(stack, right, bottom, 0.0f).color(innerColor).next().vertex(stack, right + thickness, bottom + thickness, 0.0f).color(outerColor).next().vertex(stack, right + thickness, top - thickness, 0.0f).color(outerColor).next().vertex(stack, right, top, 0.0f).color(innerColor).next();
        this.rectangleRenderContext.getBuilder().vertex(stack, left, top, 0.0f).color(innerColor).next().vertex(stack, right, top, 0.0f).color(innerColor).next().vertex(stack, right + thickness, top - thickness, 0.0f).color(outerColor).next().vertex(stack, left - thickness, top - thickness, 0.0f).color(outerColor).next();
        this.rectangleRenderContext.getBuilder().vertex(stack, left - thickness, bottom + thickness, 0.0f).color(outerColor).next().vertex(stack, right + thickness, bottom + thickness, 0.0f).color(outerColor).next().vertex(stack, right, bottom, 0.0f).color(innerColor).next().vertex(stack, left, bottom, 0.0f).color(innerColor).next();
        this.rectangleRenderContext.getBuilder().uploadToBuffer();
    }
    
    @Override
    public void renderRectangle(final Stack stack, final float left, final float top, final float right, final float bottom, final int color) {
        this.pos(left, top, right, bottom).color(color).render(stack);
    }
    
    @Override
    public void renderRectangle(final Stack stack, final float left, final float top, final float right, final float bottom, final Color color) {
        this.pos(left, top, right, bottom).color(color).render(stack);
    }
    
    @Override
    public void renderGradientVertically(final Stack stack, final float left, final float top, final float right, final float bottom, final int topColor, final int bottomColor) {
        this.pos(left, top, right, bottom).gradientVertical(topColor, bottomColor).render(stack);
    }
    
    @Override
    public void renderGradientVertically(final Stack stack, final float left, final float top, final float right, final float bottom, final float topAlpha, final float topRed, final float topGreen, final float topBlue, final float bottomAlpha, final float bottomRed, final float bottomGreen, final float bottomBlue) {
        this.pos(left, top, right, bottom).gradientVertical(topRed, topGreen, topBlue, topAlpha, bottomRed, bottomGreen, bottomBlue, bottomAlpha).render(stack);
    }
    
    @Override
    public void renderGradient(final Stack stack, final float left, final float top, final float right, final float bottom, final float leftTopAlpha, final float leftTopRed, final float leftTopGreen, final float leftTopBlue, final float rightTopAlpha, final float rightTopRed, final float rightTopGreen, final float rightTopBlue, final float leftBottomAlpha, final float leftBottomRed, final float leftBottomGreen, final float leftBottomBlue, final float rightBottomAlpha, final float rightBottomRed, final float rightBottomGreen, final float rightBottomBlue) {
        this.rectangleRenderContext.begin(stack);
        this.rectangleRenderContext.renderGradient(left, top, right, bottom, leftTopAlpha, leftTopRed, leftTopGreen, leftTopBlue, rightTopAlpha, rightTopRed, rightTopGreen, rightTopBlue, leftBottomAlpha, leftBottomRed, leftBottomGreen, leftBottomBlue, rightBottomAlpha, rightBottomRed, rightBottomGreen, rightBottomBlue);
        this.rectangleRenderContext.getBuilder().uploadToBuffer();
    }
    
    @Override
    public void setupGradient(final Stack stack, final Consumer<RectangleRenderContext> vertexBuilder) {
        this.rectangleRenderContext.begin(stack);
        vertexBuilder.accept(this.rectangleRenderContext);
        this.rectangleRenderContext.getBuilder().uploadToBuffer();
    }
    
    @Override
    public void setupSimple(final Consumer<RectangleRenderContext> vertexBuilder) {
        vertexBuilder.accept(this.rectangleRenderContext);
    }
    
    @Override
    public void render(final Stack stack) {
        this.validateBuilder();
        final boolean rounded = this.leftTopRadius > 0.0f || this.rightTopRadius > 0.0f || this.leftBottomRadius > 0.0f || this.rightBottomRadius > 0.0f;
        final float left = this.x;
        final float top = this.y;
        final float right = this.x + this.width;
        final float bottom = this.y + this.height;
        if (rounded) {
            final FloatVector4 transformVector = stack.transformVector(left, top, right, bottom);
            final RoundedGeometryBuilder.RoundedData data = this.roundedGeometryBuilder.pos(transformVector.getX() + left, transformVector.getY() + top, transformVector.getZ() + right, transformVector.getW() + bottom).radius(this.leftTopRadius, this.rightTopRadius, this.leftBottomRadius, this.rightBottomRadius).upperEdgeSoftness(this.upperEdgeSoftness).lowerEdgeSoftness(this.lowerEdgeSoftness).borderThickness(this.borderThickness).borderSoftness(this.borderSoftness).borderColor(this.borderColor).build();
            this.roundRectangleRenderer.renderSimpleRoundedRectangle(stack, data, context -> this.renderRectangle(context, left, top, right, bottom));
        }
        else {
            this.setupSimple(context -> {
                context.begin(stack);
                this.renderRectangle(context, left, top, right, bottom);
                context.getBuilder().uploadToBuffer();
                return;
            });
        }
        this.resetBuilder();
    }
    
    private void renderRectangle(final RectangleRenderContext context, final float left, final float top, final float right, final float bottom) {
        final boolean horizontalGradient = this.leftColor.isSet();
        final boolean verticalGradient = this.topColor.isSet();
        if (horizontalGradient) {
            context.renderGradientHorizontally(left, top, right, bottom, this.leftColor.getValue(), this.rightColor.getValue());
        }
        else if (verticalGradient) {
            context.renderGradientVertically(left, top, right, bottom, this.topColor.getValue(), this.bottomColor.getValue());
        }
        else {
            context.render(left, top, right, bottom, this.color);
        }
    }
}
