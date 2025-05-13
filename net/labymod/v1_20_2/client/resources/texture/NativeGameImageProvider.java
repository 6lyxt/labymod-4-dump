// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.resources.texture;

import net.labymod.api.client.resources.texture.BufferedImageProcessor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.resources.texture.AbstractGameImageProvider;

@Singleton
@Implements(GameImageProvider.class)
public class NativeGameImageProvider extends AbstractGameImageProvider
{
    @Override
    public GameImage getImage(final InputStream stream) throws IOException {
        return new NativeGameImage(ekq.a(stream));
    }
    
    @Override
    public GameImage createImage(final int width, final int height) {
        return new NativeGameImage(new ekq(width, height, true));
    }
    
    @Override
    protected GameImage create(final int channels, final int width, final int height) {
        return new NativeGameImage(new ekq((channels == 3) ? ekq.a.b : ekq.a.a, width, height, false));
    }
    
    @Override
    public GameImage getImage(final BufferedImage bufferedImage) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage));
    }
    
    @Override
    public GameImage getImage(final BufferedImage bufferedImage, final BufferedImageProcessor processor) {
        return new NativeGameImage(NativeImageUtils.asNativeImage(bufferedImage, processor));
    }
}
