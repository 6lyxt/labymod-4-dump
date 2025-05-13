// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import net.labymod.api.client.render.batch.RenderContext;
import java.awt.Color;
import net.labymod.api.util.color.format.ColorFormat;
import java.util.function.Function;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import javax.inject.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.RectangleRenderContext;

@Singleton
@Implements(RectangleRenderContext.class)
public class DefaultRectangleRenderContext extends DefaultRenderContext<RectangleRenderContext> implements RectangleRenderContext
{
    private BufferBuilder builder;
    private Stack stack;
    
    @Inject
    public DefaultRectangleRenderContext() {
    }
    
    @Override
    public RectangleRenderContext begin(final Stack stack, final RenderPhase phase, final Function<RenderPhase, BufferBuilder> builderFunction) {
        this.stack = stack;
        (this.builder = builderFunction.apply(phase)).begin(phase);
        return this;
    }
    
    @Override
    public RectangleRenderContext render(final float left, final float top, final float right, final float bottom, final float red, final float green, final float blue, final float alpha) {
        this.setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }
    
    @Override
    public RectangleRenderContext renderGradientHorizontally(final float left, final float top, final float right, final float bottom, final int leftColor, final int rightColor) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        return this.renderGradientHorizontally(left, top, right, bottom, colorFormat.normalizedAlpha(leftColor), colorFormat.normalizedRed(leftColor), colorFormat.normalizedGreen(leftColor), colorFormat.normalizedBlue(leftColor), colorFormat.normalizedAlpha(rightColor), colorFormat.normalizedRed(rightColor), colorFormat.normalizedGreen(rightColor), colorFormat.normalizedBlue(rightColor));
    }
    
    @Override
    public RectangleRenderContext renderGradientHorizontally(final float left, final float top, final float right, final float bottom, final float leftAlpha, final float leftRed, final float leftGreen, final float leftBlue, final float rightAlpha, final float rightRed, final float rightGreen, final float rightBlue) {
        this.setupGradientVertexBuilderHorizontally(left, top, right, bottom, leftAlpha, leftRed, leftGreen, leftBlue, rightAlpha, rightRed, rightGreen, rightBlue);
        return this;
    }
    
    @Override
    public RectangleRenderContext renderGradientVertically(final float left, final float top, final float right, final float bottom, final int topColor, final int bottomColor) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        return this.renderGradientVertically(left, top, right, bottom, colorFormat.normalizedAlpha(topColor), colorFormat.normalizedRed(topColor), colorFormat.normalizedGreen(topColor), colorFormat.normalizedBlue(topColor), colorFormat.normalizedAlpha(bottomColor), colorFormat.normalizedRed(bottomColor), colorFormat.normalizedGreen(bottomColor), colorFormat.normalizedBlue(bottomColor));
    }
    
    @Override
    public RectangleRenderContext renderGradientVertically(final float left, final float top, final float right, final float bottom, final float topAlpha, final float topRed, final float topGreen, final float topBlue, final float bottomAlpha, final float bottomRed, final float bottomGreen, final float bottomBlue) {
        this.setupGradientVertexBuilderVertically(left, top, right, bottom, topAlpha, topRed, topGreen, topBlue, bottomAlpha, bottomRed, bottomGreen, bottomBlue);
        return this;
    }
    
    @Override
    public RectangleRenderContext renderGradient(final float left, final float top, final float right, final float bottom, final float leftTopAlpha, final float leftTopRed, final float leftTopGreen, final float leftTopBlue, final float rightTopAlpha, final float rightTopRed, final float rightTopGreen, final float rightTopBlue, final float leftBottomAlpha, final float leftBottomRed, final float leftBottomGreen, final float leftBottomBlue, final float rightBottomAlpha, final float rightBottomRed, final float rightBottomGreen, final float rightBottomBlue) {
        this.setupGradientVertexBuilder(left, top, right, bottom, leftTopAlpha, leftTopRed, leftTopGreen, leftTopBlue, rightTopAlpha, rightTopRed, rightTopGreen, rightTopBlue, leftBottomAlpha, leftBottomRed, leftBottomGreen, leftBottomBlue, rightBottomAlpha, rightBottomRed, rightBottomGreen, rightBottomBlue);
        return this;
    }
    
    @Override
    public RectangleRenderContext render(final float left, final float top, final float right, final float bottom, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float alpha = colorFormat.normalizedAlpha(color);
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        this.setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }
    
    @Override
    public RectangleRenderContext render(final float left, final float top, final float right, final float bottom, final Color color) {
        final float alpha = color.getAlpha() / 255.0f;
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        this.setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }
    
    @Override
    public RectangleRenderContext renderOutline(final float left, final float top, final float right, final float bottom, final float thickness, final int innerColor, final int outerColor) {
        final float zOffset = this.zOffset;
        this.builder.vertex(this.stack, left - thickness, bottom + thickness, zOffset).color(outerColor).next().vertex(this.stack, left, bottom, zOffset).color(innerColor).next().vertex(this.stack, left, top, zOffset).color(innerColor).next().vertex(this.stack, left - thickness, top - thickness, zOffset).color(outerColor).next();
        this.builder.vertex(this.stack, right, bottom, zOffset).color(innerColor).next().vertex(this.stack, right + thickness, bottom + thickness, zOffset).color(outerColor).next().vertex(this.stack, right + thickness, top - thickness, zOffset).color(outerColor).next().vertex(this.stack, right, top, zOffset).color(innerColor).next();
        this.builder.vertex(this.stack, left, top, zOffset).color(innerColor).next().vertex(this.stack, right, top, zOffset).color(innerColor).next().vertex(this.stack, right + thickness, top - thickness, zOffset).color(outerColor).next().vertex(this.stack, left - thickness, top - thickness, zOffset).color(outerColor).next();
        this.builder.vertex(this.stack, left - thickness, bottom + thickness, zOffset).color(outerColor).next().vertex(this.stack, right + thickness, bottom + thickness, zOffset).color(outerColor).next().vertex(this.stack, right, bottom, zOffset).color(innerColor).next().vertex(this.stack, left, bottom, zOffset).color(innerColor).next();
        return this;
    }
    
    @Override
    public RenderContext<RectangleRenderContext> uploadToBuffer() {
        this.builder.uploadToBuffer();
        return this;
    }
    
    private void setupVertexBuilder(float left, float top, float right, float bottom, final float alpha, final float red, final float green, final float blue) {
        if (left < right) {
            final float tmpLeft = left;
            left = right;
            right = tmpLeft;
        }
        if (top < bottom) {
            final float tmpTop = top;
            top = bottom;
            bottom = tmpTop;
        }
        final float offset = this.zOffset;
        this.builder.vertex(this.stack, left, bottom, offset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, right, bottom, offset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, right, top, offset).color(red, green, blue, alpha).next();
        this.builder.vertex(this.stack, left, top, offset).color(red, green, blue, alpha).next();
    }
    
    private void setupGradientVertexBuilderHorizontally(final float left, final float top, final float right, final float bottom, final float leftAlpha, final float leftRed, final float leftGreen, final float leftBlue, final float rightAlpha, final float rightRed, final float rightGreen, final float rightBlue) {
        final float offset = this.zOffset;
        this.builder.vertex(this.stack, left, bottom, offset).color(leftRed, leftGreen, leftBlue, leftAlpha).next();
        this.builder.vertex(this.stack, right, bottom, offset).color(rightRed, rightGreen, rightBlue, rightAlpha).next();
        this.builder.vertex(this.stack, right, top, offset).color(rightRed, rightGreen, rightBlue, rightAlpha).next();
        this.builder.vertex(this.stack, left, top, offset).color(leftRed, leftGreen, leftBlue, leftAlpha).next();
    }
    
    private void setupGradientVertexBuilderVertically(final float left, final float top, final float right, final float bottom, final float topAlpha, final float topRed, final float topGreen, final float topBlue, final float bottomAlpha, final float bottomRed, final float bottomGreen, final float bottomBlue) {
        final float offset = this.zOffset;
        this.builder.vertex(this.stack, left, bottom, offset).color(bottomRed, bottomGreen, bottomBlue, bottomAlpha).next();
        this.builder.vertex(this.stack, right, bottom, offset).color(bottomRed, bottomGreen, bottomBlue, bottomAlpha).next();
        this.builder.vertex(this.stack, right, top, offset).color(topRed, topGreen, topBlue, topAlpha).next();
        this.builder.vertex(this.stack, left, top, offset).color(topRed, topGreen, topBlue, topAlpha).next();
    }
    
    private void setupGradientVertexBuilder(final float left, final float top, final float right, final float bottom, final float leftTopAlpha, final float leftTopRed, final float leftTopGreen, final float leftTopBlue, final float rightTopAlpha, final float rightTopRed, final float rightTopGreen, final float rightTopBlue, final float leftBottomAlpha, final float leftBottomRed, final float leftBottomGreen, final float leftBottomBlue, final float rightBottomAlpha, final float rightBottomRed, final float rightBottomGreen, final float rightBottomBlue) {
        final float offset = this.zOffset;
        this.builder.vertex(this.stack, left, bottom, offset).color(leftBottomRed, leftBottomGreen, leftBottomBlue, leftBottomAlpha).next();
        this.builder.vertex(this.stack, right, bottom, offset).color(rightBottomRed, rightBottomGreen, rightBottomBlue, rightBottomAlpha).next();
        this.builder.vertex(this.stack, right, top, offset).color(rightTopRed, rightTopGreen, rightTopBlue, rightTopAlpha).next();
        this.builder.vertex(this.stack, left, top, offset).color(leftTopRed, leftTopGreen, leftTopBlue, leftTopAlpha).next();
    }
}
