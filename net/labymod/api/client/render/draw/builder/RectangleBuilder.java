// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.bounds.Rectangle;

public interface RectangleBuilder<T extends RectangleBuilder<T>> extends RendererBuilder<T>
{
    default T pos(final Rectangle rectangle) {
        return this.pos(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }
    
    default T pos(final float left, final float top, final float right, final float bottom) {
        return (T)this.pos(left, top).size(right - left, bottom - top);
    }
    
    default T size(final float size) {
        return this.size(size, size);
    }
    
    T size(final float p0, final float p1);
    
    default T rounded(final float radius) {
        return this.rounded(radius, radius, radius, radius);
    }
    
    T rounded(final float p0, final float p1, final float p2, final float p3);
    
    T lowerEdgeSoftness(final float p0);
    
    T upperEdgeSoftness(final float p0);
    
    T borderThickness(final float p0);
    
    T borderSoftness(final float p0);
    
    T borderColor(final int p0);
    
    default T gradientHorizontal(final float leftRed, final float leftGreen, final float leftBlue, final float leftAlpha, final float rightRed, final float rightGreen, final float rightBlue, final float rightAlpha) {
        return this.gradientHorizontal((int)(leftRed * 255.0f), (int)(leftGreen * 255.0f), (int)(leftBlue * 255.0f), (int)(leftAlpha * 255.0f), (int)(rightRed * 255.0f), (int)(rightGreen * 255.0f), (int)(rightBlue * 255.0f), (int)(rightAlpha * 255.0f));
    }
    
    default T gradientHorizontal(final int leftRed, final int leftGreen, final int leftBlue, final int leftAlpha, final int rightRed, final int rightGreen, final int rightBlue, final int rightAlpha) {
        return this.gradientHorizontal(ColorFormat.ARGB32.pack(leftRed, leftGreen, leftBlue, leftAlpha), ColorFormat.ARGB32.pack(rightRed, rightGreen, rightBlue, rightAlpha));
    }
    
    T gradientHorizontal(final int p0, final int p1);
    
    default T gradientVertical(final float topRed, final float topGreen, final float topBlue, final float topAlpha, final float bottomRed, final float bottomGreen, final float bottomBlue, final float bottomAlpha) {
        return this.gradientVertical((int)(topRed * 255.0f), (int)(topGreen * 255.0f), (int)(topBlue * 255.0f), (int)(topAlpha * 255.0f), (int)(bottomRed * 255.0f), (int)(bottomGreen * 255.0f), (int)(bottomBlue * 255.0f), (int)(bottomAlpha * 255.0f));
    }
    
    default T gradientVertical(final int topRed, final int topGreen, final int topBlue, final int topAlpha, final int bottomRed, final int bottomGreen, final int bottomBlue, final int bottomAlpha) {
        return this.gradientVertical(ColorFormat.ARGB32.pack(topRed, topGreen, topBlue, topAlpha), ColorFormat.ARGB32.pack(bottomRed, bottomGreen, bottomBlue, bottomAlpha));
    }
    
    T gradientVertical(final int p0, final int p1);
}
