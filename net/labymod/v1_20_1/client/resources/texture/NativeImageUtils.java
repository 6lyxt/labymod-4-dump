// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.texture;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class NativeImageUtils
{
    public static ehk read(final ByteBuffer buffer) throws IOException {
        return ehk.a(buffer);
    }
    
    public static ehk read(final InputStream inputStream) throws IOException {
        return ehk.a(inputStream);
    }
    
    public static ehk asNativeImage(final BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }
    
    public static ehk asNativeImage(final BufferedImage image, final BufferedImageProcessor processor) {
        final ehk result = new ehk(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                result.a(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }
    
    public static void fillSubImage(final ehk source, final ehk destination, final int parentX, final int parentY, final int width, final int height) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                destination.a(x, y, source.a(x + parentX, y + parentY));
            }
        }
    }
    
    public static BufferedImage asBufferedImage(final ehk image) {
        final BufferedImage result = new BufferedImage(image.a(), image.b(), 2);
        for (int x = 0; x < image.a(); ++x) {
            for (int y = 0; y < image.b(); ++y) {
                result.setRGB(x, y, ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, image.a(x, y)));
            }
        }
        return result;
    }
    
    private static int processImage(final BufferedImage image, final int x, final int y) {
        return ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, image.getRGB(x, y));
    }
}
