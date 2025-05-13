// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.loader;

import java.util.Comparator;
import java.util.Iterator;
import java.net.URI;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureLoader;
import java.util.ArrayList;
import net.labymod.api.client.resources.texture.TextureRepository;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public final class TextureLoaderRepository
{
    private static final Logging LOGGER;
    private final List<LoaderEntry> textureLoaders;
    private final TextureRepository textureRepository;
    
    public TextureLoaderRepository(final TextureRepository textureRepository) {
        this.textureLoaders = new ArrayList<LoaderEntry>();
        this.textureRepository = textureRepository;
        this.registerDefaults();
    }
    
    private void registerDefaults() {
        this.registerInternalTextureLoader(0, new DataTextureLoader());
        this.registerInternalTextureLoader(0, new FaceTextureLoader());
        this.registerInternalTextureLoader(0, new McServerTextureLoader(this.textureRepository));
        this.registerInternalTextureLoader(0, new ResourceTextureLoader());
        this.registerInternalTextureLoader(0, new ProxiedHttpTextureLoader(this.textureRepository));
        this.registerInternalTextureLoader(500, new HttpTextureLoader());
        this.sortTextureLoaders();
    }
    
    public void registerTextureLoader(final int priority, final TextureLoader loader) {
        this.registerTextureLoader(priority, loader, true);
    }
    
    public boolean executeTextureLoader(String url, @Nullable final ResourceLocation location, final CompletableTextureImage target) {
        url = url.replace("\n", "");
        try {
            final URI uri = URI.create(url);
            for (final LoaderEntry entry : this.textureLoaders) {
                final TextureLoader textureLoader = entry.getTextureLoader();
                if (!textureLoader.canLoad(uri)) {
                    continue;
                }
                textureLoader.loadTexture(uri, location, target);
                return true;
            }
        }
        catch (final Throwable exception) {
            TextureLoaderRepository.LOGGER.error(exception.getMessage(), new Object[0]);
        }
        target.stopLoadingOnError();
        return false;
    }
    
    private void registerInternalTextureLoader(final int priority, final TextureLoader loader) {
        this.registerTextureLoader(priority, loader, false);
    }
    
    private void registerTextureLoader(final int priority, final TextureLoader loader, final boolean sort) {
        this.textureLoaders.add(new LoaderEntry(loader, priority));
        if (sort) {
            this.sortTextureLoaders();
        }
    }
    
    private void sortTextureLoaders() {
        this.textureLoaders.sort(Comparator.comparingInt(LoaderEntry::getPriority));
    }
    
    static {
        LOGGER = Logging.create(TextureLoaderRepository.class);
    }
}
