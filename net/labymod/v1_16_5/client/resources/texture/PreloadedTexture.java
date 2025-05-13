// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.texture;

import java.io.InputStream;
import net.labymod.api.util.io.IOUtil;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Locale;

public class PreloadedTexture extends ejy
{
    private static final String ASSETS_PATH = "/assets/%s/%s";
    
    public PreloadedTexture(final vk location) {
        super(location);
    }
    
    protected ejy.a b(final ach resourceManager) {
        final ejy.a textureImage = super.b(resourceManager);
        try {
            textureImage.c();
            return textureImage;
        }
        catch (final IOException cause) {
            final String resourcePath = String.format(Locale.ROOT, "/assets/%s/%s", this.d.b(), this.d.a());
            final InputStream resourceStream = PreloadedTexture.class.getResourceAsStream(resourcePath);
            if (resourceStream == null) {
                return new ejy.a((IOException)new FileNotFoundException(resourcePath));
            }
            final ell metadataSection = new ell(true, true);
            try {
                final ejy.a image = new ejy.a(metadataSection, det.a(resourceStream));
                IOUtil.closeSilent(resourceStream);
                return image;
            }
            catch (final IOException exception) {
                exception.initCause(cause);
                IOUtil.closeSilent(resourceStream);
                return new ejy.a(exception);
            }
        }
    }
}
