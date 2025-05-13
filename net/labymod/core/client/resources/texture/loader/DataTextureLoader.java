// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.TextureImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.client.resources.texture.TextureLoader;

public class DataTextureLoader implements TextureLoader
{
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("data");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation location, final CompletableTextureImage target) throws IOException {
        final String[] base64Parts = uri.getSchemeSpecificPart().split(",", 2);
        if (base64Parts.length >= 2) {
            String imageFormat = base64Parts[0];
            imageFormat = imageFormat.substring("image/".length(), imageFormat.indexOf(59));
            final byte[] encodedBase64 = base64Parts[1].replace("\n", "").getBytes(StandardCharsets.UTF_8);
            final byte[] data = Base64.getDecoder().decode(encodedBase64);
            try (final InputStream inputStream = new ByteArrayInputStream(data)) {
                final TextureImage textureImage = new TextureImage(inputStream, imageFormat);
                target.executeCompletableListeners(textureImage);
            }
        }
        else {
            target.stopLoadingOnError();
        }
    }
}
