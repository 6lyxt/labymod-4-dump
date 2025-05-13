// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import net.labymod.api.client.resources.texture.GameImage;

public class BufferedGameImage implements GameImage
{
    private final BufferedImage bufferedImage;
    
    public BufferedGameImage(final InputStream stream) throws IOException {
        this(ImageIO.read(stream));
    }
    
    public BufferedGameImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    
    @Override
    public int getWidth() {
        return this.bufferedImage.getWidth();
    }
    
    @Override
    public int getHeight() {
        return this.bufferedImage.getHeight();
    }
    
    @Override
    public int getRGBA(final int x, final int y) {
        return this.bufferedImage.getRGB(x, y);
    }
    
    @Override
    public void setRGBA(final int x, final int y, final int rgba) {
        this.bufferedImage.setRGB(x, y, rgba);
    }
    
    @Override
    public void setARGB(final int x, final int y, final int argb) {
        this.bufferedImage.setRGB(x, y, argb);
    }
    
    @Override
    public int getARGB(final int x, final int y) {
        return this.bufferedImage.getRGB(x, y);
    }
    
    @Override
    public GameImage getSubImage(final int x, final int y, final int width, final int height) {
        final int[] rgb = this.bufferedImage.getRGB(x, y, width, height, null, 0, width);
        final BufferedGameImage bufferedGameImage = new BufferedGameImage(new BufferedImage(width, height, 2));
        int index = 0;
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                bufferedGameImage.setARGB(w, h, rgb[index]);
                ++index;
            }
        }
        return bufferedGameImage;
    }
    
    @Override
    public void write(final String format, final Path path) throws IOException {
        ImageIO.write(this.createWritableImage(), format, IOUtil.toFile(path));
    }
    
    @Override
    public void write(final String format, final OutputStream outputStream) throws IOException {
        ImageIO.write(this.createWritableImage(), format, outputStream);
    }
    
    @Override
    public GameImage resize(final int x, final int y, final int width, final int height) {
        final BufferedImage resizedBufferedImage = new BufferedImage(width, height, this.getImage().getType());
        final Graphics2D graphics = resizedBufferedImage.createGraphics();
        graphics.drawImage(this.bufferedImage, 0, 0, width, height, null);
        graphics.dispose();
        return BufferedGameImage.IMAGE_PROVIDER.getImage(resizedBufferedImage);
    }
    
    @Override
    public BufferedImage getImage() {
        return this.bufferedImage;
    }
    
    @Override
    public void close() {
    }
    
    private BufferedImage createWritableImage() {
        final int width = this.getWidth();
        final int height = this.getHeight();
        final BufferedImage image = new BufferedImage(width, height, 2);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                image.setRGB(x, y, this.bufferedImage.getRGB(x, y));
            }
        }
        return image;
    }
}
