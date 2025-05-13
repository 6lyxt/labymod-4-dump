// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.cache;

import java.io.InputStream;
import java.nio.file.Path;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import net.labymod.api.util.function.Consumers;
import java.util.function.Consumer;
import java.io.IOException;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.GameImage;
import java.util.Map;
import net.labymod.api.util.logging.Logging;

public final class ImageMemoryCache
{
    private static final Logging LOGGER;
    private final Map<String, GameImage> cache;
    private final GameImageProvider imageProvider;
    
    public ImageMemoryCache(final Map<String, GameImage> cache) {
        this.cache = cache;
        this.imageProvider = Laby.references().gameImageProvider();
    }
    
    public void loadCacheImage(final String hash, final ThrowableSupplier<TextureImage, IOException> imageSupplier, final Consumer<GameImage> consumer) throws IOException {
        final GameImage image = this.cache.get(hash);
        if (image != null) {
            Consumers.accept(consumer, image);
            return;
        }
        final Path cachedImage = Constants.Files.CACHE.resolve(hash.substring(0, 2) + "/" + hash);
        final GameImage gameImage = this.readCachedImageFromLocalStorage(hash, cachedImage);
        if (gameImage != null) {
            Consumers.accept(consumer, gameImage);
            return;
        }
        final Path directory = cachedImage.getParent();
        if (!IOUtil.exists(directory)) {
            IOUtil.createDirectories(directory);
        }
        final TextureImage textureImage = imageSupplier.get();
        this.writeImageToLocalStorage(hash, consumer, cachedImage, textureImage);
    }
    
    public void release() {
        this.cache.clear();
    }
    
    private void writeImageToLocalStorage(final String hash, final Consumer<GameImage> consumer, final Path cachedImage, final TextureImage textureImage) throws IOException {
        final GameImage gameImage = textureImage.getGameImage();
        gameImage.write(textureImage.getFormat(), cachedImage);
        this.cache.put(hash, gameImage);
        Consumers.accept(consumer, gameImage);
    }
    
    private GameImage readCachedImageFromLocalStorage(final String hash, final Path path) throws IOException {
        if (!IOUtil.exists(path)) {
            return null;
        }
        try (final InputStream stream = IOUtil.newInputStream(path)) {
            final GameImage image = this.imageProvider.getImage(stream);
            this.cache.put(hash, image);
            return image;
        }
        catch (final Throwable throwable) {
            ImageMemoryCache.LOGGER.warn("Failed to load image \"{}\" from cache (probably corrupt), deleting it: {}: {}", path, throwable.getClass().getSimpleName(), throwable.getMessage());
            IOUtil.delete(path);
            return null;
        }
    }
    
    static {
        LOGGER = Logging.create(ImageMemoryCache.class);
    }
}
