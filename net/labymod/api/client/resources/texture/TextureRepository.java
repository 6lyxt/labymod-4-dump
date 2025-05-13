// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.labymod.api.util.function.ThrowableSupplier;
import java.io.IOException;
import net.labymod.api.Textures;
import java.util.function.Consumer;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TextureRepository
{
    CompletableResourceLocation getOrRegisterTexture(final ResourceLocation p0, final String p1);
    
    default CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final String url, final Consumer<Texture> finishHandler) {
        return this.getOrRegisterTexture(location, url, i -> i, finishHandler);
    }
    
    default CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final ResourceLocation fallbackLocation, final String url, final Consumer<Texture> finishHandler) {
        return this.getOrRegisterTexture(location, fallbackLocation, url, null, finishHandler);
    }
    
    default CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final ResourceLocation fallbackLocation, final String url, final Consumer<Texture> finishHandler, final Consumer<CompletableResourceLocation> completableResourceLocationHandler) {
        return this.getOrRegisterTexture(location, fallbackLocation, url, null, finishHandler, completableResourceLocationHandler);
    }
    
    default CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final String url, final GameImageProcessor imageModifier, final Consumer<Texture> finishHandler) {
        return this.getOrRegisterTexture(location, Textures.EMPTY, url, imageModifier, finishHandler);
    }
    
    default CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final ResourceLocation fallbackLocation, final String url, final GameImageProcessor imageModifier, final Consumer<Texture> finishHandler) {
        return this.getOrRegisterTexture(location, fallbackLocation, url, imageModifier, finishHandler, null);
    }
    
    CompletableResourceLocation getOrRegisterTexture(final ResourceLocation p0, final ResourceLocation p1, final String p2, final GameImageProcessor p3, final Consumer<Texture> p4, final Consumer<CompletableResourceLocation> p5);
    
    CompletableResourceLocation getOrRegisterTexture(final TextureDetails p0);
    
    @Deprecated(forRemoval = true, since = "4.2.17")
    void registerRuntimeTexture(final ResourceLocation p0, final Object p1);
    
    boolean hasResource(final ResourceLocation p0);
    
    void unloadNamespace(final String p0);
    
    void unloadAll();
    
    CompletableResourceLocation loadCacheResourceAsync(final String p0, final String p1, final String p2, final ResourceLocation p3);
    
    default ResourceLocation loadCacheResource(final String namespace, final String hash, final String url) throws IOException {
        return this.loadCacheResource(namespace, hash, url, loadedLocation -> {});
    }
    
    ResourceLocation loadCacheResource(final String p0, final String p1, final String p2, final Consumer<ResourceLocation> p3) throws IOException;
    
    void loadCacheImage(final String p0, final String p1, final ResourceLocation p2, final Consumer<GameImage> p3) throws IOException;
    
    void loadCacheImage(final String p0, final ThrowableSupplier<TextureImage, IOException> p1, final Consumer<GameImage> p2) throws IOException;
    
    void purgeMemoryCache();
    
    void purgeStorageCache() throws IOException;
    
    void invalidateRemoteTextures(final Predicate<String> p0);
    
    void invalidateRemoteTextures(final BiPredicate<String, CompletableResourceLocation> p0);
    
    default void register(final ResourceLocation location, final Texture texture) {
        this.register(location, texture, null);
    }
    
    void register(final ResourceLocation p0, final Texture p1, @Nullable final Runnable p2);
    
    @Deprecated(forRemoval = true, since = "4.2.17")
    default void registerTexture(final ResourceLocation location, final Object texture) {
        this.registerTexture(location, texture, null);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.17")
    void registerTexture(final ResourceLocation p0, final Object p1, final Runnable p2);
    
    void preloadTexture(final ResourceLocation p0);
    
    @ApiStatus.Experimental
    default void registerAndRelease(final ResourceLocation location, final Texture texture) {
        this.registerAndRelease(location, texture, null);
    }
    
    @ApiStatus.Experimental
    void registerAndRelease(final ResourceLocation p0, final Texture p1, final Runnable p2);
    
    default void releaseTexture(final ResourceLocation location) {
        this.releaseTexture(location, null);
    }
    
    void releaseTexture(final ResourceLocation p0, final Runnable p1);
    
    void queueTextureRelease(final ResourceLocation p0);
    
    boolean executeTextureLoader(final String p0, final ResourceLocation p1, final CompletableTextureImage p2);
    
    default boolean executeTextureLoader(final String url, final CompletableTextureImage target) {
        return this.executeTextureLoader(url, null, target);
    }
    
    void registerTextureLoader(final int p0, final TextureLoader p1);
    
    Texture getTexture(final ResourceLocation p0);
}
