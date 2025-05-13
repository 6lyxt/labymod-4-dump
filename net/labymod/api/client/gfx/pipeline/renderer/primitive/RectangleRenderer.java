// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.primitive;

import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gfx.pipeline.renderer.batch.BufferBatchable;
import net.labymod.api.client.gfx.pipeline.renderer.Renderer;

public class RectangleRenderer implements Renderer
{
    private final BufferBatchable batchable;
    
    public RectangleRenderer(final BufferBatchable batchable) {
        this.batchable = batchable;
    }
    
    public void draw(final Rectangle rectangle, final int color) {
        this.draw(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), color);
    }
    
    public void drawOutline(final Rectangle rectangle, final int outerColor, final int innerColor, final float thickness) {
        this.drawOutline(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), outerColor, innerColor, thickness);
    }
    
    public void drawOutline(final float left, final float top, final float right, final float bottom, final int outerColor, final int innerColor, final float thickness) {
        final BufferBuilder bufferBuilder = this.batchable.bufferBuilder();
        final int outerColorABGR = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, outerColor);
        final int innerColorABGR = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, innerColor);
        this.writeVertex(bufferBuilder, left - thickness, bottom + thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, left, bottom, innerColorABGR);
        this.writeVertex(bufferBuilder, left, top, innerColorABGR);
        this.writeVertex(bufferBuilder, left - thickness, top - thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, right, bottom, innerColorABGR);
        this.writeVertex(bufferBuilder, right + thickness, bottom + thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, right + thickness, top - thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, right, top, innerColorABGR);
        this.writeVertex(bufferBuilder, left, top, innerColorABGR);
        this.writeVertex(bufferBuilder, right, top, innerColorABGR);
        this.writeVertex(bufferBuilder, right + thickness, top - thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, left - thickness, top - thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, left - thickness, bottom + thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, right + thickness, bottom + thickness, outerColorABGR);
        this.writeVertex(bufferBuilder, right, bottom, innerColorABGR);
        this.writeVertex(bufferBuilder, left, bottom, innerColorABGR);
    }
    
    public void draw(final float left, final float top, final float right, final float bottom, final int red, final int green, final int blue, final int alpha) {
        this.drawInternal(left, top, right, bottom, ColorFormat.ABGR32.pack(red, green, blue, alpha));
    }
    
    public void draw(final float left, final float top, final float right, final float bottom, final float red, final float green, final float blue, final float alpha) {
        this.drawInternal(left, top, right, bottom, ColorFormat.ABGR32.pack(red, green, blue, alpha));
    }
    
    public void draw(final float left, final float top, final float right, final float bottom, final int color) {
        this.drawInternal(left, top, right, bottom, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color));
    }
    
    public void drawWithGradient(final GradientDirection direction, final Rectangle rectangle, final int color0, final int color1) {
        this.drawWithGradient(direction, rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), color0, color1);
    }
    
    public void drawWithGradient(final GradientDirection direction, final float left, final float top, final float right, final float bottom, final int color0, final int color1) {
        final BufferBuilder bufferBuilder = this.batchable.bufferBuilder();
        final int color0AGBR = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color0);
        final int color1AGBR = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color1);
        switch (direction) {
            case TOP_TO_BOTTOM: {
                this.writeVertex(bufferBuilder, left, top, color0AGBR);
                this.writeVertex(bufferBuilder, left, bottom, color1AGBR);
                this.writeVertex(bufferBuilder, right, bottom, color1AGBR);
                this.writeVertex(bufferBuilder, right, top, color0AGBR);
                break;
            }
            case BOTTOM_TO_TOP: {
                this.writeVertex(bufferBuilder, left, top, color1AGBR);
                this.writeVertex(bufferBuilder, left, bottom, color0AGBR);
                this.writeVertex(bufferBuilder, right, bottom, color0AGBR);
                this.writeVertex(bufferBuilder, right, top, color1AGBR);
                break;
            }
            case LEFT_TO_RIGHT: {
                this.writeVertex(bufferBuilder, left, top, color0AGBR);
                this.writeVertex(bufferBuilder, left, bottom, color0AGBR);
                this.writeVertex(bufferBuilder, right, bottom, color1AGBR);
                this.writeVertex(bufferBuilder, right, top, color1AGBR);
                break;
            }
            case RIGHT_TO_LEFT: {
                this.writeVertex(bufferBuilder, left, top, color1AGBR);
                this.writeVertex(bufferBuilder, left, bottom, color1AGBR);
                this.writeVertex(bufferBuilder, right, bottom, color0AGBR);
                this.writeVertex(bufferBuilder, right, top, color0AGBR);
                break;
            }
        }
    }
    
    private void drawInternal(float left, float top, float right, float bottom, final int packedColor) {
        final BufferBuilder bufferBuilder = this.batchable.bufferBuilder();
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
        this.writeVertex(bufferBuilder, left, top, packedColor);
        this.writeVertex(bufferBuilder, left, bottom, packedColor);
        this.writeVertex(bufferBuilder, right, bottom, packedColor);
        this.writeVertex(bufferBuilder, right, top, packedColor);
    }
    
    private void writeVertex(final BufferBuilder bufferBuilder, final float x, final float y, final int color) {
        bufferBuilder.putFloat(x, y, this.batchable.getZOffset()).putFloat(0.0f, 0.0f).putInt(color).endVertex();
    }
}
