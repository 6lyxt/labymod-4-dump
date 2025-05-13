// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.screenshot;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.math.vector.FloatVector2;

public class ScreenshotUtil
{
    public static FloatVector2 maxSize(final FloatVector2 dimension, final int maxWidth, final int maxHeight) {
        final int width = (int)dimension.getX();
        final int height = (int)dimension.getY();
        if (width <= maxWidth && height <= maxHeight) {
            return dimension;
        }
        final double scale = Math.min(maxWidth / (double)width, maxHeight / (double)height);
        final int newWidth = (int)(width * scale);
        final int newHeight = (int)(height * scale);
        return new FloatVector2((float)newWidth, (float)newHeight);
    }
    
    public static GameImage maxSize(final GameImage image, final int maxWidth, final int maxHeight) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        if (width <= maxWidth && height <= maxHeight) {
            return image;
        }
        final double scale = Math.min(maxWidth / (double)width, maxHeight / (double)height);
        final int newWidth = (int)(width * scale);
        final int newHeight = (int)(height * scale);
        return image.resize(0, 0, newWidth, newHeight);
    }
    
    public static BufferedImage maxSize(final BufferedImage image, final int maxWidth, final int maxHeight) {
        final GameImage resizedImage = maxSize(GameImage.IMAGE_PROVIDER.getImage(image), maxWidth, maxHeight);
        return resizedImage.getImage();
    }
    
    public static BufferedImage resize(final BufferedImage image, final int width, final int height) {
        final BufferedImage resized = new BufferedImage(width, height, image.getType());
        resized.createGraphics().drawImage(image, 0, 0, width, height, null);
        return resized;
    }
    
    public static GameImage resize(final GameImage image, final int width, final int height) {
        return image.resize(0, 0, width, height);
    }
}
