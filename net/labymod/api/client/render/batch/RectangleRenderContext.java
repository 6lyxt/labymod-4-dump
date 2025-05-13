// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.batch;

import java.awt.Color;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.client.render.vertex.BufferBuilder;
import java.util.function.Function;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RectangleRenderContext extends RenderContext<RectangleRenderContext>
{
    default RectangleRenderContext begin(final Stack stack) {
        return this.begin(stack, type -> this.getBuilder());
    }
    
    default RectangleRenderContext begin(final Stack stack, final RenderPhase phase) {
        return this.begin(stack, phase, type -> this.getBuilder());
    }
    
    default RectangleRenderContext begin(final Stack stack, final Function<RenderPhase, BufferBuilder> builderFunction) {
        return this.begin(stack, RenderPhases.NO_TEXTURED_RECTANGLE_PHASE, builderFunction);
    }
    
    RectangleRenderContext begin(final Stack p0, final RenderPhase p1, final Function<RenderPhase, BufferBuilder> p2);
    
    RectangleRenderContext render(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    default RectangleRenderContext renderGradientHorizontally(final Rectangle rectangle, final int leftColor, final int rightColor) {
        return this.renderGradientHorizontally(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), leftColor, rightColor);
    }
    
    RectangleRenderContext renderGradientHorizontally(final float p0, final float p1, final float p2, final float p3, final int p4, final int p5);
    
    RectangleRenderContext renderGradientHorizontally(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11);
    
    RectangleRenderContext renderGradientVertically(final float p0, final float p1, final float p2, final float p3, final int p4, final int p5);
    
    default RectangleRenderContext renderGradientVertically(final Rectangle rectangle, final int topColor, final int bottomColor) {
        return this.renderGradientVertically(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), topColor, bottomColor);
    }
    
    RectangleRenderContext renderGradientVertically(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11);
    
    RectangleRenderContext renderGradient(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11, final float p12, final float p13, final float p14, final float p15, final float p16, final float p17, final float p18, final float p19);
    
    default RectangleRenderContext render(final Rectangle rectangle, final int color) {
        return this.render(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), color);
    }
    
    RectangleRenderContext render(final float p0, final float p1, final float p2, final float p3, final int p4);
    
    RectangleRenderContext render(final float p0, final float p1, final float p2, final float p3, final Color p4);
    
    RectangleRenderContext renderOutline(final float p0, final float p1, final float p2, final float p3, final float p4, final int p5, final int p6);
    
    default RectangleRenderContext renderBackgroundGradient(final float left, final float top, final float right, final float bottom) {
        return this.renderGradientVertically(left, top, right, bottom, -1072689136, -804253680);
    }
}
