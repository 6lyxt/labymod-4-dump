// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import java.awt.Color;
import net.labymod.api.util.color.format.ColorFormat;

public interface RendererBuilder<T extends RendererBuilder<T>>
{
    T pos(final float p0, final float p1);
    
    default T color(final float red, final float green, final float blue, final float alpha) {
        return this.color((int)(red * 255.0f), (int)(green * 255.0f), (int)(blue * 255.0f), (int)(alpha * 255.0f));
    }
    
    default T color(final int red, final int green, final int blue, final int alpha) {
        return this.color(ColorFormat.ARGB32.pack(red, green, blue, alpha));
    }
    
    default T color(final Color color) {
        return this.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    T color(final int p0);
    
    void validateBuilder();
    
    void resetBuilder();
}
