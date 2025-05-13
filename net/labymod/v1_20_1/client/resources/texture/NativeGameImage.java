// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.texture;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Path;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.resources.texture.GameImage;

record NativeGameImage(ehk image) implements GameImage {
    @Override
    public int getWidth() {
        return this.image.a();
    }
    
    @Override
    public int getHeight() {
        return this.image.b();
    }
    
    @Override
    public int getRGBA(final int x, final int y) {
        return this.image.a(x, y);
    }
    
    @Override
    public void setRGBA(final int x, final int y, final int rgba) {
        this.image.a(x, y, rgba);
    }
    
    @Override
    public void setARGB(final int x, final int y, final int argb) {
        final int abgr = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, argb);
        this.image.a(x, y, abgr);
    }
    
    @Override
    public int getARGB(final int x, final int y) {
        return ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, this.image.a(x, y));
    }
    
    @Override
    public GameImage getSubImage(final int x, final int y, final int width, final int height) {
        final ehk destination = new ehk(width, height, true);
        NativeImageUtils.fillSubImage(this.image, destination, x, y, width, height);
        return new NativeGameImage(destination);
    }
    
    @Override
    public void write(final String format, final Path path) throws IOException {
        this.image.a(path);
    }
    
    @Override
    public void write(final String format, final OutputStream outputStream) throws IOException {
        ((NativeImageAccessor)this.image).writeToStream(outputStream);
    }
    
    @Override
    public GameImage resize(final int x, final int y, final int width, final int height) {
        final BufferedImage bufferedImage = this.getImage();
        final BufferedImage newImage = new BufferedImage(width, height, 2);
        final Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);
        graphics.dispose();
        return NativeGameImage.IMAGE_PROVIDER.getImage(newImage);
    }
    
    @Override
    public BufferedImage getImage() {
        return NativeImageUtils.asBufferedImage(this.image);
    }
    
    @Override
    public boolean isFreed() {
        return ((NativeImageAccessor)this.image).isFreed();
    }
    
    @Override
    public void close() {
        this.image.close();
    }
}
