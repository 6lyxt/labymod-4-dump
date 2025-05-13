// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.component.format.TextColor;

public class FilterColor
{
    private boolean shouldHighlightMessage;
    private float red;
    private float green;
    private float blue;
    
    public FilterColor(final boolean shouldHighlightMessage) {
        this(shouldHighlightMessage, 0.2f, 0.8f, 0.95f);
    }
    
    public FilterColor(final boolean shouldHighlightMessage, final float red, final float green, final float blue) {
        this.shouldHighlightMessage = shouldHighlightMessage;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public boolean shouldHighlightMessage() {
        return this.shouldHighlightMessage;
    }
    
    public void setShouldHighlightMessage(final boolean shouldHighlightMessage) {
        this.shouldHighlightMessage = shouldHighlightMessage;
    }
    
    public float getRed() {
        return this.red;
    }
    
    public void setRed(final float red) {
        this.red = this.validColor(red);
    }
    
    public float getGreen() {
        return this.green;
    }
    
    public void setGreen(final float green) {
        this.green = this.validColor(green);
    }
    
    public float getBlue() {
        return this.blue;
    }
    
    public void setBlue(final float blue) {
        this.blue = this.validColor(blue);
    }
    
    private float validColor(final float value) {
        return Math.max(0.0f, Math.min(1.0f, value));
    }
    
    public void set(final TextColor textColor) {
        if (textColor == null) {
            this.shouldHighlightMessage = false;
            return;
        }
        this.shouldHighlightMessage = true;
        final Color color = textColor.color();
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final int colorValue = color.get();
        this.setRed(colorFormat.normalizedRed(colorValue));
        this.setGreen(colorFormat.normalizedGreen(colorValue));
        this.setBlue(colorFormat.normalizedBlue(colorValue));
    }
    
    public int getRGBA() {
        return this.getRGBA(1.0f);
    }
    
    public int getRGBA(final float alpha) {
        if (!this.shouldHighlightMessage) {
            return 0;
        }
        final int a = (int)(alpha * 255.0f);
        final int r = (int)(this.red * 255.0f);
        final int g = (int)(this.green * 255.0f);
        final int b = (int)(this.blue * 255.0f);
        return ColorFormat.ARGB32.pack(r, g, b, a);
    }
}
