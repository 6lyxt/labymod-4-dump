// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.color.format;

import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import java.awt.Color;

public interface ColorFormat
{
    public static final int COMPONENT_BITS = 8;
    public static final int COMPONENT_MASK = 255;
    public static final float COMPONENT_RANGE = 255.0f;
    public static final ColorFormat ARGB32 = new ARGB32ColorFormat();
    public static final ColorFormat ABGR32 = new ABGR32ColorFormat();
    
    int red(final int p0);
    
    int green(final int p0);
    
    int blue(final int p0);
    
    int alpha(final int p0);
    
    default float normalizedRed(final int value) {
        return this.normalize(this.red(value));
    }
    
    default float normalizedGreen(final int value) {
        return this.normalize(this.green(value));
    }
    
    default float normalizedBlue(final int value) {
        return this.normalize(this.blue(value));
    }
    
    default float normalizedAlpha(final int value) {
        return this.normalize(this.alpha(value));
    }
    
    default int pack(@NotNull final Color color, final int alpha) {
        return this.pack(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    default int pack(@NotNull final net.labymod.api.util.Color color, final int alpha) {
        return this.pack(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    default int pack(final int value, final int alpha) {
        return this.pack(this.red(value), this.green(value), this.blue(value), alpha);
    }
    
    default int pack(final int red, final int green, final int blue) {
        return this.pack(red, green, blue, 255);
    }
    
    int pack(final int p0, final int p1, final int p2, final int p3);
    
    default int pack(final float red, final float green, final float blue, final float alpha) {
        return this.pack(this.normalize(red), this.normalize(green), this.normalize(blue), this.normalize(alpha));
    }
    
    default int add(final int sourceValue, final int deltaRed, final int deltaGreen, final int deltaBlue, final int deltaAlpha) {
        final int red = this.red(sourceValue) + deltaRed;
        final int green = this.green(sourceValue) + deltaGreen;
        final int blue = this.blue(sourceValue) + deltaBlue;
        final int alpha = this.alpha(sourceValue) + deltaAlpha;
        return this.pack(this.clamp(red), this.clamp(green), this.clamp(blue), this.clamp(alpha));
    }
    
    default int sub(final int sourceValue, final int deltaRed, final int deltaGreen, final int deltaBlue, final int deltaAlpha) {
        final int red = this.red(sourceValue) - deltaRed;
        final int green = this.green(sourceValue) - deltaGreen;
        final int blue = this.blue(sourceValue) - deltaBlue;
        final int alpha = this.alpha(sourceValue) - deltaAlpha;
        return this.pack(this.clamp(red), this.clamp(green), this.clamp(blue), this.clamp(alpha));
    }
    
    default int mul(final int sourceValue, final float factorRed, final float factorGreen, final float factorBlue, final float factorAlpha) {
        final float red = this.normalizedRed(sourceValue) * factorRed;
        final float green = this.normalizedGreen(sourceValue) * factorGreen;
        final float blue = this.normalizedBlue(sourceValue) * factorBlue;
        final float alpha = this.normalizedAlpha(sourceValue) * factorAlpha;
        return this.pack(this.clampNormalized(red), this.clampNormalized(green), this.clampNormalized(blue), this.clampNormalized(alpha));
    }
    
    int withAlpha(final int p0, final int p1);
    
    default PackedColor createPackedColor(final int value) {
        return new PackedColor(this, value);
    }
    
    default int packTo(final ColorFormat destinationFormat, final int value) {
        final int red = this.red(value);
        final int green = this.green(value);
        final int blue = this.blue(value);
        final int alpha = this.alpha(value);
        return destinationFormat.pack(red, green, blue, alpha);
    }
    
    default int clamp(final int value) {
        return MathHelper.clamp(value, 0, 255);
    }
    
    default float clampNormalized(final float value) {
        return MathHelper.clamp(value, 0.0f, 1.0f);
    }
    
    default int normalize(final float value) {
        return (int)(value * 255.0f) & 0xFF;
    }
    
    default float normalize(final int value) {
        return value / 255.0f;
    }
}
