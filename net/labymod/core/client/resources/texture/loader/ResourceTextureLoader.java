// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;
import net.labymod.api.client.resources.texture.TextureLoader;

public class ResourceTextureLoader implements TextureLoader
{
    @Override
    public boolean canLoad(final URI uri) {
        return uri.getScheme().equals("resource");
    }
    
    @Override
    public void loadTexture(final URI uri, @Nullable final ResourceLocation resourceLocation, final CompletableTextureImage target) throws IOException {
        final String[] parts = uri.getSchemeSpecificPart().split("://", 2);
        if (parts.length != 2) {
            target.stopLoadingOnError();
            return;
        }
        final String namespace = parts[0];
        final String path = parts[1];
        final ResourceLocation location = ResourceLocation.create(namespace, path);
        target.executeCompletableListeners(new TextureImage(location));
    }
}
