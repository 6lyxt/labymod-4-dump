// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import net.labymod.api.client.render.batch.RectangleRenderContext;
import java.util.function.Consumer;
import java.awt.Color;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.render.draw.builder.DirectRendererBuilder;
import net.labymod.api.client.render.draw.builder.RectangleBuilder;

@Referenceable
public interface RectangleRenderer extends RectangleBuilder<RectangleRenderer>, DirectRendererBuilder
{
    BatchRectangleRenderer beginBatch(final Stack p0);
    
    BatchRectangleRenderer beginBatch(final Stack p0, final RenderPhase p1);
    
    default void renderVerticalLine(final Stack stack, final float x, float y, float size, final int color) {
        if (size < y) {
            final float tmp = y;
            y = size;
            size = tmp;
        }
        this.renderRectangle(stack, x, y + 1.0f, x + 1.0f, size, color);
    }
    
    default void renderHorizontalLine(final Stack stack, float x, final float y, float size, final int color) {
        if (size < x) {
            final float tmp = x;
            x = size;
            size = tmp;
        }
        this.renderRectangle(stack, x, size, y + 1.0f, size + 1.0f, color);
    }
    
    default void renderOutline(final Stack stack, final Rectangle rectangle, final int color, final float thickness) {
        this.renderOutline(stack, rectangle, color, color, thickness);
    }
    
    default void renderOutline(final Stack stack, final Rectangle rectangle, final int outerColor, final int innerColor, final float thickness) {
        this.renderOutline(stack, rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(), outerColor, innerColor, thickness);
    }
    
    default void renderOutline(final Stack stack, final float left, final float top, final float right, final float bottom, final int color, final float thickness) {
        this.renderOutline(stack, left, top, right, bottom, color, color, thickness);
    }
    
    void renderOutline(final Stack p0, final float p1, final float p2, final float p3, final float p4, final int p5, final int p6, final float p7);
    
    void renderRectangle(final Stack p0, final Rectangle p1, final int p2);
    
    void renderRectangle(final Stack p0, final Rectangle p1, final Color p2);
    
    void renderRectangle(final Stack p0, final float p1, final float p2, final float p3, final float p4, final int p5);
    
    void renderRectangle(final Stack p0, final float p1, final float p2, final float p3, final float p4, final Color p5);
    
    @Deprecated
    void renderGradientVertically(final Stack p0, final float p1, final float p2, final float p3, final float p4, final int p5, final int p6);
    
    @Deprecated
    void renderGradientVertically(final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11, final float p12);
    
    @Deprecated
    void renderGradient(final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11, final float p12, final float p13, final float p14, final float p15, final float p16, final float p17, final float p18, final float p19, final float p20);
    
    void setupGradient(final Stack p0, final Consumer<RectangleRenderContext> p1);
    
    void setupSimple(final Consumer<RectangleRenderContext> p0);
}
