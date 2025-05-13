// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.texture;

import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.labymod.core.client.resources.BufferedGameImage;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.texture.AbstractGameImageProvider;

@Singleton
@Implements(GameImageProvider.class)
public class BufferedGameImageProvider extends AbstractGameImageProvider
{
    @Override
    public GameImage getImage(final InputStream stream) throws IOException {
        final BufferedGameImage image = new BufferedGameImage(stream);
        stream.close();
        return image;
    }
    
    @Override
    public GameImage createImage(final int width, final int height) {
        return new BufferedGameImage(new BufferedImage(width, height, 2));
    }
    
    @Override
    public GameImage getImage(final BufferedImage bufferedImage) {
        return new BufferedGameImage(bufferedImage);
    }
    
    @Override
    public GameImage getImage(final BufferedImage bufferedImage, final BufferedImageProcessor processor) {
        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                bufferedImage.setRGB(x, y, processor.getPixelColor(bufferedImage, x, y));
            }
        }
        return new BufferedGameImage(bufferedImage);
    }
    
    @Override
    protected GameImage create(final int channels, final int width, final int height) {
        return new BufferedGameImage(new BufferedImage(width, height, (channels == 3) ? 1 : 2));
    }
}
