// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.Laby;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.GameTickProvider;

public class Color
{
    private static final GameTickProvider TICK_PROVIDER;
    private static final float DEFAULT_CHROMA_SPEED = 1.0f;
    public static final Color WHITE;
    public static final Color LIGHT_GRAY;
    public static final Color GRAY;
    public static final Color DARK_GRAY;
    public static final Color BLACK;
    public static final Color RED;
    public static final Color PINK;
    public static final Color ORANGE;
    public static final Color YELLOW;
    public static final Color GREEN;
    public static final Color MAGENTA;
    public static final Color CYAN;
    public static final Color BLUE;
    private final int value;
    private final int alpha;
    private final int hue;
    private final int saturation;
    private final int brightness;
    private boolean rainbow;
    private float rainbowSpeed;
    private float lastRainbowValueAt;
    private int lastRainbowValue;
    
    private Color(final int value, final int alpha, final int hue, final int saturation, final int brightness) {
        this.rainbowSpeed = 1.0f;
        this.value = (value & 0xFFFFFF);
        this.alpha = alpha;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }
    
    private Color(final int value, final int alpha, final int[] hsb) {
        this(value, alpha, hsb[0], hsb[1], hsb[2]);
    }
    
    private Color(final int alpha, final int hue, final int saturation, final int brightness) {
        this(HSBtoRGB(hue, saturation, brightness), alpha, hue, saturation, brightness);
    }
    
    public static Color of(final int value) {
        return of(value, ColorFormat.ARGB32.alpha(value));
    }
    
    public static Color of(final int value, final int alpha) {
        final int strippedValue = value & 0xFFFFFF;
        return new Color(strippedValue, alpha, RGBtoHSB(strippedValue));
    }
    
    public static Color of(final String hex) {
        return of(Integer.decode(hex) & 0xFFFFFF, 255);
    }
    
    public static Color of(final java.awt.Color color) {
        return ofRGB(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static Color ofHSB(final float hue, final float saturation, final float brightness) {
        return ofHSB(hue, saturation, brightness, 255);
    }
    
    public static Color ofHSB(final float hue, final float saturation, final float brightness, final int alpha) {
        return ofHSB((int)(hue * 360.0f), (int)(saturation * 100.0f), (int)(brightness * 100.0f), alpha);
    }
    
    public static Color ofHSB(final int hue, final int saturation, final int brightness) {
        return ofHSB(hue, saturation, brightness, 255);
    }
    
    public static Color ofHSB(final int hue, final int saturation, final int brightness, final int alpha) {
        final int value = HSBtoRGB(hue, saturation, brightness);
        return new Color(value, alpha, hue, saturation, brightness);
    }
    
    public static Color ofRGB(final int red, final int green, final int blue) {
        return of(ColorFormat.ARGB32.pack(red, green, blue, 255));
    }
    
    public static Color ofRGB(final int red, final int green, final int blue, final int alpha) {
        return of(ColorFormat.ARGB32.pack(red, green, blue, alpha));
    }
    
    public static Color ofRGB(final float red, final float green, final float blue) {
        return ofRGB(red, green, blue, 255);
    }
    
    public static Color ofRGB(final float red, final float green, final float blue, final int alpha) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        return ofRGB(colorFormat.normalize(red), colorFormat.normalize(green), colorFormat.normalize(blue), alpha);
    }
    
    public int getRed() {
        return ColorFormat.ARGB32.red(this.get());
    }
    
    public int getGreen() {
        return ColorFormat.ARGB32.green(this.get());
    }
    
    public int getBlue() {
        return ColorFormat.ARGB32.blue(this.get());
    }
    
    public int getAlpha() {
        return this.alpha;
    }
    
    public int get() {
        if (this.rainbow) {
            final float currentTick = Color.TICK_PROVIDER.get();
            if (currentTick != this.lastRainbowValueAt) {
                this.lastRainbowValueAt = currentTick;
                final int rainbowSaturation = (this.saturation == 0) ? 75 : this.saturation;
                final int rainbowBrightness = (this.brightness == 0) ? 75 : this.brightness;
                final int rainbowValue = HSBtoRGB(this.getHue(), rainbowSaturation, rainbowBrightness) & 0xFFFFFF;
                this.lastRainbowValue = ColorFormat.ARGB32.withAlpha(rainbowValue, this.alpha);
            }
            return this.lastRainbowValue;
        }
        return ColorFormat.ARGB32.withAlpha(this.value, this.alpha);
    }
    
    public int getHue() {
        return this.getHue(Color.TICK_PROVIDER.get());
    }
    
    private int getHue(final float tick) {
        if (this.rainbow) {
            final float rainbowSpeed = this.rainbowSpeed * 2.0f / 100.0f * tick;
            return (int)(MathHelper.sin(rainbowSpeed + this.hue) * 179.0f + 180.0f);
        }
        return this.hue;
    }
    
    public int getSaturation() {
        return this.saturation;
    }
    
    public int getBrightness() {
        return this.brightness;
    }
    
    public boolean isChroma() {
        return this.rainbow;
    }
    
    public float getChromaSpeed() {
        return this.rainbowSpeed;
    }
    
    public boolean isDefaultChromaSpeed() {
        return this.rainbowSpeed == 1.0f;
    }
    
    public Color withAlpha(final int alpha) {
        if (this.alpha == alpha) {
            return this;
        }
        final Color color = new Color(this.value, alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (this.isChroma()) {
            color.rainbow = true;
        }
        return color;
    }
    
    public Color withAlpha(final float alpha) {
        return this.withAlpha((int)(alpha * 255.0f));
    }
    
    public Color withHue(final int hue) {
        if (this.hue == hue) {
            return this;
        }
        final Color color = new Color(this.alpha, hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (this.isChroma()) {
            color.rainbow = true;
        }
        return color;
    }
    
    public Color withSaturation(final int saturation) {
        if (this.saturation == saturation) {
            return this;
        }
        final Color color = new Color(this.alpha, this.hue, saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (this.isChroma()) {
            color.rainbow = true;
        }
        return color;
    }
    
    public Color withBrightness(final int brightness) {
        if (this.brightness == brightness) {
            return this;
        }
        final Color color = new Color(this.alpha, this.hue, this.saturation, brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (this.isChroma()) {
            color.rainbow = true;
        }
        return color;
    }
    
    public Color withoutAlpha() {
        if (this.alpha == 255) {
            return this;
        }
        final Color color = new Color(this.value, 255, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = this.rainbowSpeed;
        if (this.isChroma()) {
            color.rainbow = true;
        }
        return color;
    }
    
    public Color withChroma() {
        return this.withChroma(this.rainbowSpeed);
    }
    
    public Color withChroma(final float speed) {
        if (this.rainbow && this.rainbowSpeed == speed) {
            return this;
        }
        final Color color = new Color(this.value, this.alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = speed;
        color.rainbow = true;
        return color;
    }
    
    public Color withoutChroma() {
        return this.withoutChroma(this.rainbowSpeed);
    }
    
    public Color withoutChroma(final float speed) {
        if (!this.rainbow && this.rainbowSpeed == speed) {
            return this;
        }
        final Color color = new Color(this.value, this.alpha, this.hue, this.saturation, this.brightness);
        color.rainbowSpeed = speed;
        color.rainbow = false;
        return color;
    }
    
    public Color resetChromaSpeed() {
        return this.rainbow ? this.withChroma(1.0f) : this.withoutChroma(1.0f);
    }
    
    public int getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "Color{value=" + this.value + ", alpha=" + this.alpha + ", rainbow=" + this.rainbow;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Color color = (Color)o;
        return this.value == color.value && this.alpha == color.alpha && this.rainbow == color.rainbow && this.rainbowSpeed == color.rainbowSpeed;
    }
    
    @Override
    public int hashCode() {
        int result = this.value;
        result = 31 * result + this.alpha;
        result = 31 * result + (this.rainbow ? 1 : 0);
        result = 31 * result + ((this.rainbowSpeed != 0.0f) ? Float.floatToIntBits(this.rainbowSpeed) : 0);
        return result;
    }
    
    private static int getViableColor(final int color) {
        return Math.max(0, Math.min(255, color));
    }
    
    public static int[] RGBtoHSB(final int red, final int green, final int blue) {
        return RGBtoHSB(ColorFormat.ARGB32.pack(red, green, blue));
    }
    
    public static int[] RGBtoHSB(final int rgb) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float r = colorFormat.normalizedRed(rgb);
        final float g = colorFormat.normalizedGreen(rgb);
        final float b = colorFormat.normalizedBlue(rgb);
        final float M = (r > g) ? Math.max(r, b) : Math.max(g, b);
        final float m = (r < g) ? Math.min(r, b) : Math.min(g, b);
        final float c = M - m;
        float h;
        if (M == r) {
            for (h = (g - b) / c; h < 0.0f; h += 6.0f) {}
            h %= 6.0f;
        }
        else if (M == g) {
            h = (b - r) / c + 2.0f;
        }
        else {
            h = (r - g) / c + 4.0f;
        }
        h *= 60.0f;
        final float s = c / M;
        return new int[] { (c == 0.0f) ? 0 : ((int)h), (int)(s * 100.0f), (int)(M * 100.0f) };
    }
    
    public static int HSBtoRGB(final float hue, final float saturation, final float brightness) {
        return HSBtoRGB((int)(hue * 360.0f), (int)(saturation * 100.0f), (int)(brightness * 100.0f));
    }
    
    public static int HSBtoRGB(int hue, final int saturation, final int brightness) {
        hue %= 360;
        final float s = saturation / 100.0f;
        final float v = brightness / 100.0f;
        final float c = v * s;
        final float h = hue / 60.0f;
        final float x = c * (1.0f - Math.abs(h % 2.0f - 1.0f));
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        switch (hue / 60) {
            case 0: {
                r = c;
                g = x;
                b = 0.0f;
                break;
            }
            case 1: {
                r = x;
                g = c;
                b = 0.0f;
                break;
            }
            case 2: {
                r = 0.0f;
                g = c;
                b = x;
                break;
            }
            case 3: {
                r = 0.0f;
                g = x;
                b = c;
                break;
            }
            case 4: {
                r = x;
                g = 0.0f;
                b = c;
                break;
            }
            case 5: {
                r = c;
                g = 0.0f;
                b = x;
                break;
            }
            default: {
                return 0;
            }
        }
        final float m = v - c;
        return ColorFormat.ARGB32.pack((int)((r + m) * 255.0f), (int)((g + m) * 255.0f), (int)((b + m) * 255.0f));
    }
    
    public static int withAlpha(final int color, final int alpha) {
        return ColorFormat.ARGB32.withAlpha(color, alpha);
    }
    
    static {
        TICK_PROVIDER = Laby.references().gameTickProvider();
        WHITE = ofRGB(255, 255, 255);
        LIGHT_GRAY = ofRGB(192, 192, 192);
        GRAY = ofRGB(128, 128, 128);
        DARK_GRAY = ofRGB(64, 64, 64);
        BLACK = ofRGB(0, 0, 0);
        RED = ofRGB(255, 0, 0);
        PINK = ofRGB(255, 175, 175);
        ORANGE = ofRGB(255, 200, 0);
        YELLOW = ofRGB(255, 255, 0);
        GREEN = ofRGB(0, 255, 0);
        MAGENTA = ofRGB(255, 0, 255);
        CYAN = ofRGB(0, 255, 255);
        BLUE = ofRGB(0, 0, 255);
    }
}
