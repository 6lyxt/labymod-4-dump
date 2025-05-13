// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.client.resources.Resources;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Path;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.format.ColorFormat;

public interface GameImage
{
    public static final GameImageProvider IMAGE_PROVIDER = Laby.references().gameImageProvider();
    
    int getWidth();
    
    int getHeight();
    
    @Deprecated(forRemoval = true, since = "4.2.6")
    int getRGBA(final int p0, final int p1);
    
    @Deprecated(forRemoval = true, since = "4.2.6")
    void setRGBA(final int p0, final int p1, final int p2);
    
    void setARGB(final int p0, final int p1, final int p2);
    
    int getARGB(final int p0, final int p1);
    
    default void setNoAlpha(final int x, final int y) {
        this.setARGB(x, y, ColorFormat.ARGB32.pack(this.getARGB(x, y), 255));
    }
    
    default void setNoAlpha(final int x, final int y, final int width, final int height) {
        for (int dx = 0; dx < width; ++dx) {
            for (int dy = 0; dy < height; ++dy) {
                this.setNoAlpha(x + dx, y + dy);
            }
        }
    }
    
    default void blendPixel(final int x, final int y, final int overlayARGB) {
        this.setARGB(x, y, ColorUtil.blendColors(this.getARGB(x, y), overlayARGB));
    }
    
    default void drawImage(final GameImage image, final int dx, final int dy, final int sx, final int sy, final int width, final int height) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                final int argb = image.getARGB(x + sx, y + sy);
                this.setARGB(x + dx, y + dy, argb);
            }
        }
    }
    
    default void copyFrom(final GameImage texture) {
        this.drawImage(texture, 0, 0, 0, 0, texture.getWidth(), texture.getHeight());
    }
    
    default void fillRect(final int x, final int y, final int width, final int height, final int rgba) {
        for (int dx = 0; dx < width; ++dx) {
            for (int dy = 0; dy < height; ++dy) {
                this.setARGB(x + dx, y + dy, rgba);
            }
        }
    }
    
    default void copyRect(final int fromX, final int fromY, final int offsetX, final int offsetY, final int width, final int height, final boolean mirrorX, final boolean mirrorY) {
        this.copyRect(this, fromX, fromY, fromX + offsetX, fromY + offsetY, width, height, mirrorX, mirrorY);
    }
    
    default void copyRect(final GameImage image, final int fromX, final int fromY, final int toX, final int toY, final int width, final int height, final boolean mirrorX, final boolean mirrorY) {
        for (int yRead = 0; yRead < height; ++yRead) {
            for (int xRead = 0; xRead < width; ++xRead) {
                final int xWrite = mirrorX ? (width - 1 - xRead) : xRead;
                final int yWrite = mirrorY ? (height - 1 - yRead) : yRead;
                final int argb = this.getARGB(fromX + xRead, fromY + yRead);
                image.setARGB(toX + xWrite, toY + yWrite, argb);
            }
        }
    }
    
    default void drawImageOverlay(final GameImage topImage, final int dx, final int dy, final int sx, final int sy, final int width, final int height) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                final int top = topImage.getARGB(x + sx, y + sy);
                this.blendPixel(x + dx, y + dy, top);
            }
        }
    }
    
    default void flipHorizontally(final int x, final int y, final int width, final int height) {
        final int right = x + width - 1;
        for (int dx = 0; dx < width / 2; ++dx) {
            for (int dy = 0; dy < height; ++dy) {
                final int l = x + dx;
                final int r = right - dx;
                if (l != r) {
                    final int lr = this.getARGB(l, y + dy);
                    final int rr = this.getARGB(r, y + dy);
                    this.setARGB(l, y + dy, rr);
                    this.setARGB(r, y + dy, lr);
                }
            }
        }
    }
    
    default void swap(final int x1, final int y1, final int x2, final int y2, final int width, final int height) {
        for (int dx = 0; dx < width; ++dx) {
            for (int dy = 0; dy < height; ++dy) {
                final int rgba1 = this.getARGB(x1 + dx, y1 + dy);
                final int rgba2 = this.getARGB(x2 + dx, y2 + dy);
                this.setARGB(x1 + dx, y1 + dy, rgba2);
                this.setARGB(x2 + dx, y2 + dy, rgba1);
            }
        }
    }
    
    default int getAverageColor() {
        return this.getAverageColor(0, 0, this.getWidth(), this.getHeight());
    }
    
    default int getAverageColor(final int x1, final int y1, final int x2, final int y2) {
        long red = 0L;
        long green = 0L;
        long blue = 0L;
        int amount = (x2 - x1) * (y2 - y1);
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int x3 = x1; x3 < x2; ++x3) {
            for (int y3 = y1; y3 < y2; ++y3) {
                final int rgba = this.getARGB(x3, y3);
                if (colorFormat.alpha(rgba) == 0) {
                    --amount;
                }
                else {
                    red += colorFormat.red(rgba);
                    green += colorFormat.green(rgba);
                    blue += colorFormat.blue(rgba);
                }
            }
        }
        if (amount == 0) {
            return 0;
        }
        return colorFormat.pack((int)(red / amount), (int)(green / amount), (int)(blue / amount));
    }
    
    GameImage getSubImage(final int p0, final int p1, final int p2, final int p3);
    
    default GameImage scale(final int width, final int height) {
        final GameImage scaledImage = GameImage.IMAGE_PROVIDER.createImage(width, height);
        final int srcWidth = this.getWidth();
        final int srcHeight = this.getHeight();
        final float scaleX = srcWidth / (float)width;
        final float scaleY = srcHeight / (float)height;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                try {
                    final int x2 = (int)(x * scaleX);
                    final int y2 = (int)(y * scaleY);
                    final int x3 = Math.min(srcWidth, MathHelper.ceil((x + 1) * scaleX));
                    final int y3 = Math.min(srcHeight, MathHelper.ceil((y + 1) * scaleY));
                    final int color = this.getAverageColor(x2, y2, x3, y3);
                    scaledImage.setARGB(x, y, color);
                }
                catch (final Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return scaledImage;
    }
    
    void write(final String p0, final Path p1) throws IOException;
    
    void write(final String p0, final OutputStream p1) throws IOException;
    
    GameImage resize(final int p0, final int p1, final int p2, final int p3);
    
    BufferedImage getImage();
    
    default boolean isFreed() {
        return false;
    }
    
    void close();
    
    default void uploadTextureAt(final ResourceLocation resourceLocation) {
        final Resources resources = Laby.labyAPI().renderPipeline().resources();
        final TextureRepository textureRepository = resources.textureRepository();
        final GameImageTexture.Factory gameImageTextureFactory = resources.gameImageTextureFactory();
        final GameImageTexture gameImageTexture = gameImageTextureFactory.create(resourceLocation, this);
        textureRepository.register(resourceLocation, gameImageTexture);
    }
}
