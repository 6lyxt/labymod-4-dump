// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.texture;

import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class NativeImageUtils
{
    public static fkf read(final ByteBuffer buffer) throws IOException {
        return fkf.a(buffer);
    }
    
    public static fkf read(final InputStream inputStream) throws IOException {
        return fkf.a(inputStream);
    }
    
    public static fkf asNativeImage(final BufferedImage image) {
        return asNativeImage(image, NativeImageUtils::processImage);
    }
    
    public static fkf asNativeImage(final BufferedImage image, final BufferedImageProcessor processor) {
        final fkf result = new fkf(image.getWidth(), image.getHeight(), true);
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                result.b(x, y, processor.getPixelColor(image, x, y));
            }
        }
        return result;
    }
    
    public static void fillSubImage(final fkf source, final fkf destination, final int parentX, final int parentY, final int width, final int height) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                destination.b(x, y, source.a(x + parentX, y + parentY));
            }
        }
    }
    
    public static BufferedImage asBufferedImage(final fkf image) {
        final BufferedImage result = new BufferedImage(image.a(), image.b(), 2);
        for (int x = 0; x < image.a(); ++x) {
            for (int y = 0; y < image.b(); ++y) {
                result.setRGB(x, y, image.a(x, y));
            }
        }
        return result;
    }
    
    private static int processImage(final BufferedImage image, final int x, final int y) {
        return image.getRGB(x, y);
    }
}
