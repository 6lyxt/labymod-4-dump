// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.util.color.format.ColorFormat;

public interface Blaze3DFog
{
    void enable();
    
    void disable();
    
    default void setColor(final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        final float alpha = colorFormat.normalizedAlpha(color);
        this.setColor(red, green, blue, alpha);
    }
    
    void setColor(final float p0, final float p1, final float p2, final float p3);
    
    void setStartDistance(final float p0);
    
    void setEndDistance(final float p0);
    
    void setDensity(final float p0);
}
