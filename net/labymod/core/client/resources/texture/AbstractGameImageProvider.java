// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture;

import net.labymod.api.util.color.format.ColorFormat;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.Buffers;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.GameImageProvider;

public abstract class AbstractGameImageProvider implements GameImageProvider
{
    @Override
    public GameImage loadImage(final Texture texture) {
        if (texture instanceof final GameImageTexture gameImageTexture) {
            final GameImage image = gameImageTexture.getImage();
            if (image != null) {
                return image;
            }
        }
        final GFXBridge gfx = Laby.gfx();
        gfx.bindTexture2D(TextureId.of(texture.getTextureId()));
        final int format = gfx.getTexLevelParameterI(TextureTarget.TEXTURE_2D, 0, 4099);
        final int width = gfx.getTexLevelParameterI(TextureTarget.TEXTURE_2D, 0, 4096);
        final int height = gfx.getTexLevelParameterI(TextureTarget.TEXTURE_2D, 0, 4097);
        final int channels = (format == 6407) ? 3 : 4;
        final ByteBuffer buffer = Buffers.createByteBuffer(width * height * channels);
        gfx.getTextureImage(TextureTarget.TEXTURE_2D, 0, (channels == 3) ? 6407 : 6408, 5121, buffer);
        final GameImage image2 = this.create(channels, width, height);
        this.writeImage(buffer, image2, channels);
        return image2;
    }
    
    protected abstract GameImage create(final int p0, final int p1, final int p2);
    
    private void writeImage(final ByteBuffer buffer, final GameImage image, final int channels) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                final int index = (x + y * width) * channels;
                if (channels == 4) {
                    image.setARGB(x, y, ColorFormat.ABGR32.packTo(ColorFormat.ARGB32, buffer.getInt(index)));
                }
                else {
                    final int blue = buffer.get(index) & 0xFF;
                    final int green = buffer.get(index + 1) & 0xFF;
                    final int red = buffer.get(index + 2) & 0xFF;
                    image.setARGB(x, y, ColorFormat.ARGB32.pack(red, green, blue, 255));
                }
            }
        }
    }
}
