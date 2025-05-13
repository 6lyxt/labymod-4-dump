// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.util.function.Functional;
import net.labymod.api.client.component.format.NamedTextColor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import net.labymod.api.util.color.format.ColorFormat;
import java.awt.Color;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.component.format.TextColor;

public class ColorUtil
{
    private static final String HEX_FORMAT = "#%02x%02x%02x";
    private static final String HEX_ALPHA_FORMAT = "#%02x%02x%02x%02x";
    public static final char LEGACY_COLOR_CHAR_PREFIX = '§';
    public static final TextColor[] DEFAULT_COLORS;
    public static final int WHITE_ARGB;
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static final int WHITE_RGBA;
    private static final Int2ObjectMap<TextColor> DEFAULT_COLOR_LOOKUP;
    
    private ColorUtil() {
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final int hex) {
        return packARGB(hex, 255);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final int hex, final int alpha) {
        return toValue(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final int red, final int green, final int blue) {
        return toValue(red, green, blue, 255);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final int red, final int green, final int blue, final int alpha) {
        return packARGB(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final float red, final float green, final float blue, final float alpha) {
        return packARGB(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int toValue(final Color color, final int alpha) {
        return toValue(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(final int hex, final int alpha) {
        return toValue(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(final int red, final int green, final int blue) {
        return packARGB(red, green, blue, 255);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(final int red, final int green, final int blue, final int alpha) {
        return ColorFormat.ARGB32.pack(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packARGB(final float red, final float green, final float blue, final float alpha) {
        return ColorFormat.ARGB32.pack(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packABGR(final int red, final int green, final int blue, final int alpha) {
        return ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int packABGR(final float red, final float green, final float blue, final float alpha) {
        return ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getRed(final int color) {
        return ColorFormat.ARGB32.normalizedRed(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getRedInt(final int color) {
        return ColorFormat.ARGB32.red(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getGreen(final int color) {
        return ColorFormat.ARGB32.normalizedGreen(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getGreenInt(final int color) {
        return ColorFormat.ARGB32.green(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getBlue(final int color) {
        return ColorFormat.ARGB32.normalizedBlue(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getBlueInt(final int color) {
        return ColorFormat.ARGB32.blue(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static float getAlpha(final int color) {
        return ColorFormat.ARGB32.normalizedAlpha(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int getAlphaInt(final int color) {
        return ColorFormat.ARGB32.alpha(color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int mapARGBtoABGR(final int color) {
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, color);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int mapABGRtoARGB(final int color) {
        return ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, color);
    }
    
    public static float[] toRGBA(final int color) {
        final ColorFormat format = ColorFormat.ARGB32;
        return new float[] { format.normalizedRed(color), format.normalizedGreen(color), format.normalizedBlue(color), format.normalizedAlpha(color) };
    }
    
    public static void fillArray(final int[] channels, final int color) {
        channels[0] = (color >> 16 & 0xFF);
        channels[1] = (color >> 8 & 0xFF);
        channels[2] = (color & 0xFF);
        if (channels.length == 4) {
            channels[3] = (color >> 24 & 0xFF);
        }
    }
    
    public static int rgbToRgba(final int rgb) {
        return 0xFF000000 | rgb;
    }
    
    public static boolean isNoColor(final int color) {
        return color == 0;
    }
    
    public static boolean isNoColor(final float red, final float green, final float blue, final float alpha) {
        return red == 0.0f && green == 0.0f && blue == 0.0f && alpha == 0.0f;
    }
    
    public static String removeLegacyColors(final String text) {
        return text.replaceAll("§[a-z0-9]", "");
    }
    
    public static String rgbToHex(final int color) {
        final String[] hex = { null };
        splitIntoChannel(color, (red, green, blue, alpha) -> hex[0] = rgbToHex(red, green, blue));
        return hex[0];
    }
    
    public static String rgbToHex(final int red, final int green, final int blue) {
        return String.format(Locale.ROOT, "#%02x%02x%02x", red, green, blue);
    }
    
    public static String rgbToHex(final int red, final int green, final int blue, final int alpha) {
        return String.format(Locale.ROOT, "#%02x%02x%02x%02x", red, green, blue, alpha);
    }
    
    public static void splitIntoChannel(final int color, @NotNull final ColorChannel colorChannel) {
        final int red = color >> 16 & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color & 0xFF;
        final int alpha = color >> 24 & 0xFF;
        colorChannel.channel(red, green, blue, alpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int add(final int color, final int deltaRed, final int deltaGreen, final int deltaBlue, final int deltaAlpha) {
        return ColorFormat.ARGB32.add(color, deltaRed, deltaGreen, deltaBlue, deltaAlpha);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.7")
    public static int multiply(final int color, final float factorRed, final float factorGreen, final float factorBlue, final float factorAlpha) {
        return ColorFormat.ARGB32.mul(color, factorRed, factorGreen, factorBlue, factorAlpha);
    }
    
    public static int combineAlpha(final int color) {
        return combineAlpha(color, Laby.labyAPI().renderPipeline().getAlpha());
    }
    
    public static int combineAlpha(int color, final float alpha) {
        if (color == -1) {
            color = ColorUtil.WHITE_ARGB;
        }
        if (alpha == 1.0f) {
            return color;
        }
        if (alpha == 0.0f) {
            return packARGB(color, 0);
        }
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float res = colorFormat.normalizedAlpha(color) * alpha;
        return colorFormat.pack(color, MathHelper.ceil(res * 255.0f));
    }
    
    public static int adjustedColor(final int color) {
        return ((color & 0xFC000000) == 0x0) ? (color | 0xFF000000) : color;
    }
    
    public static int getRainbowColor(final float speed) {
        return getRainbowColor(speed, Laby.labyAPI().renderPipeline().gameTickProvider().get());
    }
    
    public static int getRainbowColor(final float speed, final float delta) {
        final float rainbowSpeed = speed / 100.0f * delta;
        final int red = (int)(Math.sin(rainbowSpeed + 0.0f) * 127.0 + 128.0);
        final int green = (int)(Math.sin(rainbowSpeed + 2.0f) * 127.0 + 128.0);
        final int blue = (int)(Math.sin(rainbowSpeed + 4.0f) * 127.0 + 128.0);
        return ColorFormat.ARGB32.pack(red, green, blue);
    }
    
    public static int blendColors(final int destination, final int source) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float sourceAlpha = colorFormat.normalizedAlpha(source);
        final float destinationAlpha = colorFormat.normalizedAlpha(destination);
        final float outAlpha = sourceAlpha + destinationAlpha * (1.0f - sourceAlpha);
        if (outAlpha < 0.001f) {
            return 0;
        }
        final int sourceRed = colorFormat.red(source);
        final int sourceGreen = colorFormat.green(source);
        final int sourceBlue = colorFormat.blue(source);
        final int destinationRed = colorFormat.red(destination);
        final int destinationGreen = colorFormat.green(destination);
        final int destinationBlue = colorFormat.blue(destination);
        final int outRed = (int)((sourceRed * sourceAlpha + destinationRed * destinationAlpha * (1.0f - sourceAlpha)) / outAlpha);
        final int outGreen = (int)((sourceGreen * sourceAlpha + destinationGreen * destinationAlpha * (1.0f - sourceAlpha)) / outAlpha);
        final int outBlue = (int)((sourceBlue * sourceAlpha + destinationBlue * destinationAlpha * (1.0f - sourceAlpha)) / outAlpha);
        return colorFormat.pack(outRed, outGreen, outBlue, colorFormat.normalize(outAlpha));
    }
    
    public static TextColor getClosestDefaultTextColor(final TextColor color) {
        return getClosestDefaultTextColor(color.color());
    }
    
    public static TextColor getClosestDefaultTextColor(final net.labymod.api.util.Color color) {
        final TextColor textColor = (TextColor)ColorUtil.DEFAULT_COLOR_LOOKUP.get(color.getValue());
        if (textColor != null) {
            return textColor;
        }
        float matchedDistance = Float.MAX_VALUE;
        TextColor match = ColorUtil.DEFAULT_COLORS[0];
        for (final TextColor potential : ColorUtil.DEFAULT_COLORS) {
            final float distance = distance(color, potential.color());
            if (distance < matchedDistance) {
                match = potential;
                matchedDistance = distance;
            }
            if (distance == 0.0f) {
                break;
            }
        }
        return match;
    }
    
    public static int lerp(final int color, final int previousColor, final float delta) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final boolean colorPresent = color != 0;
        final int alpha = colorPresent ? colorFormat.alpha(color) : 0;
        final int red = colorFormat.red(colorPresent ? color : previousColor);
        final int green = colorFormat.green(colorPresent ? color : previousColor);
        final int blue = colorFormat.blue(colorPresent ? color : previousColor);
        final int previousAlpha = colorFormat.alpha(previousColor);
        final int previousRed = colorFormat.red(previousColor);
        final int previousGreen = colorFormat.green(previousColor);
        final int previousBlue = colorFormat.blue(previousColor);
        final int newAlpha = MathHelper.lerp(alpha, previousAlpha, delta);
        final int newRed = MathHelper.lerp(red, previousRed, delta);
        final int newGreen = MathHelper.lerp(green, previousGreen, delta);
        final int newBlue = MathHelper.lerp(blue, previousBlue, delta);
        return colorFormat.pack(newRed, newGreen, newBlue, newAlpha);
    }
    
    public static int hsvToRgb(final float hue, final float saturation, final float value) {
        final int sector = (int)(hue * 6.0f) % 6;
        final float fractionalPart = hue * 6.0f - sector;
        final float p = value * (1.0f - saturation);
        final float q = value * (1.0f - fractionalPart * saturation);
        final float t = value * (1.0f - (1.0f - fractionalPart) * saturation);
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;
        switch (sector) {
            case 0: {
                red = value;
                green = t;
                blue = p;
                break;
            }
            case 1: {
                red = q;
                green = value;
                blue = p;
                break;
            }
            case 2: {
                red = p;
                green = value;
                blue = t;
                break;
            }
            case 3: {
                red = p;
                green = q;
                blue = value;
                break;
            }
            case 4: {
                red = t;
                green = p;
                blue = value;
                break;
            }
            case 5: {
                red = value;
                green = p;
                blue = q;
                break;
            }
            default: {
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
            }
        }
        return ColorFormat.ARGB32.pack(MathHelper.clamp((int)(red * 255.0f), 0, 255), MathHelper.clamp((int)(green * 255.0f), 0, 255), MathHelper.clamp((int)(blue * 255.0f), 0, 255), 255);
    }
    
    private static float distance(final net.labymod.api.util.Color self, final net.labymod.api.util.Color other) {
        final float selfHue = self.getHue() / 360.0f;
        final float otherHue = other.getHue() / 360.0f;
        final float hueDistance = 3.0f * Math.min(Math.abs(selfHue - otherHue), 1.0f - Math.abs(selfHue - otherHue));
        final float saturationDiff = self.getSaturation() / 100.0f - other.getSaturation() / 100.0f;
        final float brightnessDiff = self.getBrightness() / 100.0f - other.getBrightness() / 100.0f;
        return hueDistance * hueDistance + saturationDiff * saturationDiff + brightnessDiff * brightnessDiff;
    }
    
    public static int lerpedColor(final long transitionDuration, final float delta, final LssProperty<Integer> colorProperty) {
        if (transitionDuration <= 0L) {
            return colorProperty.get();
        }
        final int currentColor = colorProperty.get();
        final int previousColor = colorProperty.getPreviousLerpedAccessedValue();
        final float progress = colorProperty.getProgress(delta, transitionDuration);
        int color;
        if (progress == 1.0f) {
            color = currentColor;
        }
        else {
            color = lerp(currentColor, previousColor, progress);
        }
        colorProperty.setLastLerpedAccessedValue(color);
        return color;
    }
    
    public static int invertColor(final int argb) {
        final int r = argb >> 16 & 0xFF;
        final int g = argb >> 8 & 0xFF;
        final int b = argb & 0xFF;
        final int a = argb >> 24 & 0xFF;
        final double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255.0;
        final int invertedColor = (luminance > 0.5) ? -16777216 : -1;
        return a << 24 | (invertedColor & 0xFFFFFF);
    }
    
    static {
        DEFAULT_COLORS = new TextColor[] { NamedTextColor.BLACK, NamedTextColor.DARK_BLUE, NamedTextColor.DARK_GREEN, NamedTextColor.DARK_AQUA, NamedTextColor.DARK_RED, NamedTextColor.DARK_PURPLE, NamedTextColor.GOLD, NamedTextColor.GRAY, NamedTextColor.DARK_GRAY, NamedTextColor.BLUE, NamedTextColor.GREEN, NamedTextColor.AQUA, NamedTextColor.RED, NamedTextColor.LIGHT_PURPLE, NamedTextColor.YELLOW, NamedTextColor.WHITE };
        WHITE_ARGB = ColorFormat.ARGB32.pack(255, 255, 255, 255);
        WHITE_RGBA = toValue(NamedTextColor.WHITE.getValue());
        DEFAULT_COLOR_LOOKUP = Functional.of((Int2ObjectMap)new Int2ObjectOpenHashMap(ColorUtil.DEFAULT_COLORS.length), map -> {
            final TextColor[] default_COLORS = ColorUtil.DEFAULT_COLORS;
            int i = 0;
            for (int length = default_COLORS.length; i < length; ++i) {
                final TextColor defaultColor = default_COLORS[i];
                map.put(defaultColor.getValue(), (Object)defaultColor);
            }
        });
    }
    
    @FunctionalInterface
    public interface ColorChannel
    {
        void channel(final int p0, final int p1, final int p2, final int p3);
    }
}
