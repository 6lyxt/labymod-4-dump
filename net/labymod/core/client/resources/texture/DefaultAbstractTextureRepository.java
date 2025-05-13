// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture;

import java.lang.invoke.CallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.SwitchBootstraps;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;
import net.labymod.api.client.resources.texture.TextureLoader;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Textures;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.client.resources.Resources;
import java.io.IOException;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import java.util.List;
import java.util.ArrayList;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.core.client.resources.AbstractResourceLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;
import java.util.Iterator;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.GameImageProcessor;
import net.labymod.api.client.resources.texture.Texture;
import java.util.function.Consumer;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import java.util.concurrent.Executors;
import net.labymod.api.client.resources.texture.GameImage;
import java.util.concurrent.ConcurrentHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.util.HashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import java.util.concurrent.ExecutorService;
import net.labymod.core.client.resources.texture.loader.TextureLoaderRepository;
import net.labymod.core.client.resources.texture.cache.ImageMemoryCache;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.Map;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.texture.TextureRepository;

public abstract class DefaultAbstractTextureRepository implements TextureRepository
{
    protected static final Logging LOGGER;
    private static final long TEXTURE_RELEASE_QUEUE_TIME;
    protected final LabyAPI labyAPI;
    protected final GameImageTexture.Factory gameImageTextureFactory;
    protected final GameImageProvider gameImageProvider;
    private final Map<String, CompletableResourceLocation> completableMap;
    @Deprecated(forRemoval = true, since = "4.2.17")
    private final Map<ResourceLocation, Object> runtimeTextures;
    private final Map<ResourceLocation, TextureImage> fallbackTextures;
    private final ImageMemoryCache imageMemoryCache;
    private final TextureLoaderRepository textureLoaderRepository;
    private final Map<ResourceLocation, RemoteTexture> remoteTextures;
    private final ExecutorService executor;
    protected TextureImage fallbackTextureImage;
    private final Object2LongMap<ResourceLocation> queuedTextureReleases;
    
    protected DefaultAbstractTextureRepository(final LabyAPI labyAPI, final GameImageTexture.Factory gameImageTextureFactory, final GameImageProvider gameImageProvider) {
        this.fallbackTextures = new HashMap<ResourceLocation, TextureImage>();
        this.queuedTextureReleases = (Object2LongMap<ResourceLocation>)new Object2LongOpenHashMap();
        this.labyAPI = labyAPI;
        this.gameImageTextureFactory = gameImageTextureFactory;
        this.gameImageProvider = gameImageProvider;
        this.completableMap = new HashMap<String, CompletableResourceLocation>();
        this.remoteTextures = new ConcurrentHashMap<ResourceLocation, RemoteTexture>();
        this.imageMemoryCache = new ImageMemoryCache(new ConcurrentHashMap<String, GameImage>());
        this.runtimeTextures = new HashMap<ResourceLocation, Object>();
        this.executor = Executors.newFixedThreadPool(5);
        labyAPI.eventBus().registerListener(this);
        this.textureLoaderRepository = new TextureLoaderRepository(this);
    }
    
    @Subscribe
    public void loadFallbackTextureImage(final ResourceReloadEvent event) {
        if (this.fallbackTextureImage == null && event.type() == ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS && event.phase() == Phase.PRE) {
            this.loadFallbackTextureImage();
        }
    }
    
    @Override
    public CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final String url) {
        return this.getOrRegisterTexture(location, url, null);
    }
    
    @Override
    public CompletableResourceLocation getOrRegisterTexture(final ResourceLocation location, final ResourceLocation fallbackLocation, final String url, final GameImageProcessor imageModifier, final Consumer<Texture> finishHandler, final Consumer<CompletableResourceLocation> completableResourceLocationHandler) {
        return this.getOrRegisterTexture(TextureDetails.builder().withLocation(location).withFallbackLocation(fallbackLocation).withUrl(url).withImageProcessor(imageModifier).withFinishHandler(finishHandler).withLocationHandler(completableResourceLocationHandler).build());
    }
    
    @Override
    public CompletableResourceLocation getOrRegisterTexture(final TextureDetails details) {
        final ResourceLocation location = details.location();
        final RemoteTexture cached = (details.registerStrategy() == TextureDetails.RegisterStrategy.REGISTER) ? this.remoteTextures.get(location) : null;
        if (cached != null) {
            return cached.location();
        }
        final CompletableResourceLocation completableLocation = new CompletableResourceLocation(details.fallbackLocation());
        details.acceptLocation(completableLocation);
        final RemoteTexture texture = new RemoteTexture(details.url(), completableLocation);
        this.putRemoteTexture(location, texture);
        if (ThreadSafe.isRenderThread()) {
            this.getOrRegisterTexture(details, texture);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.getOrRegisterTexture(details, texture));
        }
        return completableLocation;
    }
    
    private void getOrRegisterTexture(final TextureDetails details, final RemoteTexture remoteTexture) {
        final CompletableTextureImage completableImage = new CompletableTextureImage(this.createFallbackTextureImage(details.fallbackLocation()));
        completableImage.addCompletableListener(() -> {
            if (completableImage.hasError()) {
                remoteTexture.location().stopLoadingOnError();
            }
            if (completableImage.isLoading()) {
                return;
            }
            else {
                final GameImage gameImage = completableImage.getCompleted().getGameImage();
                final GameImage gameImage2 = details.processImage(gameImage);
                final ResourceLocation location = details.location();
                final GameImageTexture imageTexture = this.gameImageTextureFactory.create(location, gameImage2);
                final Metadata metadata = location.metadata();
                metadata.set("width", gameImage2.getWidth());
                metadata.set("height", gameImage2.getHeight());
                final TextureDetails.RegisterStrategy strategy = details.registerStrategy();
                if (strategy == TextureDetails.RegisterStrategy.REGISTER) {
                    this.register(location, imageTexture, () -> {
                        details.finish(imageTexture);
                        remoteTexture.location().executeCompletableListeners(location);
                        this.putRemoteTexture(location, remoteTexture);
                    });
                }
                else {
                    this.registerAndRelease(location, imageTexture, () -> {
                        details.finish(imageTexture);
                        remoteTexture.location().executeCompletableListeners(location);
                        this.putRemoteTexture(location, remoteTexture);
                    });
                }
                return;
            }
        });
        this.download(remoteTexture.url(), details.location(), completableImage, true);
        remoteTexture.setDownloadStarted(true);
    }
    
    private void putRemoteTexture(final ResourceLocation location, final RemoteTexture texture) {
        this.remoteTextures.put(location, texture);
    }
    
    @Override
    public void registerRuntimeTexture(final ResourceLocation location, final Object texture) {
        ThreadSafe.ensureRenderThread();
        final Object textureObj = this.runtimeTextures.get(location);
        if (textureObj != null) {
            return;
        }
        this.registerTexture(location, texture);
        this.runtimeTextures.put(location, texture);
    }
    
    @Override
    public boolean hasResource(final ResourceLocation location) {
        ThreadSafe.ensureRenderThread();
        final boolean registeredAsRemoteTexture = this.remoteTextures.containsKey(location);
        if (registeredAsRemoteTexture || this.runtimeTextures.containsKey(location)) {
            return true;
        }
        for (final CompletableResourceLocation crl : this.completableMap.values()) {
            if (!crl.isLoading() && crl.getCompleted().equals(location)) {
                return true;
            }
        }
        return false;
    }
    
    protected ResourceLocation unwrap(final ResourceLocation location) {
        return switch (/* invokedynamic(!) */ProcyonInvokeDynamicHelper_4.invoke(location, false)) {
            case 0 -> {
                final ThemeResourceLocation themeResourceLocation = (ThemeResourceLocation)location;
                yield this.unwrap(themeResourceLocation.resource());
            }
            case 1 -> {
                final AbstractResourceLocation abstractResourceLocation = (AbstractResourceLocation)location;
                yield this.unwrap(abstractResourceLocation.getDelegate());
            }
            case 2 -> {
                final AnimatedResourceLocation animatedResourceLocation = (AnimatedResourceLocation)location;
                yield this.unwrap(animatedResourceLocation.getCurrentResourceLocation());
            }
            default -> location;
        };
    }
    
    @Override
    public void unloadNamespace(final String namespace) {
        ThreadSafe.ensureRenderThread();
        final List<ResourceLocation> toRemove = new ArrayList<ResourceLocation>();
        for (final ResourceLocation location : this.remoteTextures.keySet()) {
            if (location.getNamespace().equals(namespace)) {
                toRemove.add(location);
            }
        }
        final Map<ResourceLocation, RemoteTexture> map = new HashMap<ResourceLocation, RemoteTexture>(this.remoteTextures);
        for (final ResourceLocation resourceLocation : toRemove) {
            final RemoteTexture remoteTexture = map.remove(resourceLocation);
            if (remoteTexture != null) {
                this.releaseTexture(resourceLocation);
            }
        }
        this.remoteTextures.putAll(map);
    }
    
    @Override
    public void unloadAll() {
        ThreadSafe.ensureRenderThread();
        this.remoteTextures.keySet().forEach(this::releaseTexture);
        this.remoteTextures.clear();
        this.runtimeTextures.keySet().forEach(this::releaseTexture);
        this.runtimeTextures.clear();
    }
    
    @Override
    public CompletableResourceLocation loadCacheResourceAsync(final String namespace, final String hash, final String url, final ResourceLocation loadingResource) {
        final CompletableResourceLocation completableLocation = new CompletableResourceLocation(loadingResource);
        if (ThreadSafe.isRenderThread()) {
            this.loadCacheResourcesAsync(namespace, hash, url, completableLocation, loadingResource);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.loadCacheResourcesAsync(namespace, hash, url, completableLocation, loadingResource));
        }
        return completableLocation;
    }
    
    private void loadCacheResourcesAsync(final String namespace, final String hash, final String url, final CompletableResourceLocation location, final ResourceLocation loadingResource) {
        CollectionHelper.removeIf(this.completableMap.entrySet(), entry -> entry.getValue().getCompleted() == null);
        CompletableResourceLocation completableLocation = this.completableMap.get(hash);
        if (completableLocation == null) {
            final CompletableResourceLocation finalCompletableLocation;
            completableLocation = (finalCompletableLocation = new CompletableResourceLocation(loadingResource));
            this.completableMap.put(hash, completableLocation);
            this.executor.execute(() -> {
                try {
                    this.loadCacheResource(namespace, hash, url, completed -> {
                        finalCompletableLocation.executeCompletableListeners(completed);
                        location.executeCompletableListeners(finalCompletableLocation.getCompleted());
                    });
                }
                catch (final IOException exception) {
                    exception.printStackTrace();
                    finalCompletableLocation.stopLoading();
                }
                return;
            });
        }
        location.executeCompletableListeners(completableLocation.getCompleted());
    }
    
    @Override
    public ResourceLocation loadCacheResource(final String namespace, final String hash, final String url, final Consumer<ResourceLocation> finishHandler) throws IOException {
        final Resources resources = this.labyAPI.renderPipeline().resources();
        final ResourceLocation location = resources.resourceLocationFactory().create(namespace, "cached/" + hash);
        this.loadCacheImage(hash, url, location, image -> {
            final GameImageTexture bufferedTexture = resources.gameImageTextureFactory().create(location, image);
            final Metadata metadata = location.metadata();
            metadata.set("width", image.getWidth());
            metadata.set("height", image.getHeight());
            this.register(location, bufferedTexture, () -> finishHandler.accept(location));
            return;
        });
        return location;
    }
    
    @Override
    public void loadCacheImage(final String hash, final String url, final ResourceLocation location, final Consumer<GameImage> gameImageConsumer) throws IOException {
        this.loadCacheImage(hash, () -> this.downloadGameImage(url, location), gameImageConsumer);
    }
    
    @Override
    public void loadCacheImage(final String hash, final ThrowableSupplier<TextureImage, IOException> imageSupplier, final Consumer<GameImage> gameImageConsumer) throws IOException {
        this.imageMemoryCache.loadCacheImage(hash, imageSupplier, gameImageConsumer);
    }
    
    @Override
    public void purgeMemoryCache() {
        ThreadSafe.ensureRenderThread();
        this.imageMemoryCache.release();
        this.completableMap.clear();
    }
    
    @Override
    public void purgeStorageCache() throws IOException {
    }
    
    @Override
    public void queueTextureRelease(final ResourceLocation location) {
        ThreadSafe.executeOnRenderThread(() -> this.queuedTextureReleases.put((Object)location, TimeUtil.getMillis() + DefaultAbstractTextureRepository.TEXTURE_RELEASE_QUEUE_TIME));
    }
    
    @Override
    public void invalidateRemoteTextures(final Predicate<String> urlTester) {
        this.invalidateRemoteTextures((url, location) -> urlTester.test(url));
    }
    
    @Override
    public void invalidateRemoteTextures(final BiPredicate<String, CompletableResourceLocation> urlTester) {
        ThreadSafe.ensureRenderThread();
        CollectionHelper.removeIf(this.remoteTextures.values(), texture -> {
            if (urlTester.test(texture.url(), texture.location())) {
                if (!texture.location().isLoading()) {
                    this.releaseTexture(texture.location().getCompleted());
                }
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    private TextureImage downloadGameImage(final String url, final ResourceLocation location) {
        final CompletableTextureImage completable = new CompletableTextureImage(this.useFallbackTextureImage());
        this.download(url, location, completable, false);
        return completable.getCompleted();
    }
    
    protected void download(final String url, final ResourceLocation location, final CompletableTextureImage completableTextureImage, final boolean asynchronous) {
        if (asynchronous) {
            this.executor.execute(() -> this.executeTextureLoader(url, location, completableTextureImage));
        }
        else {
            this.executeTextureLoader(url, location, completableTextureImage);
        }
    }
    
    protected TextureImage useFallbackTextureImage() {
        return this.fallbackTextureImage;
    }
    
    private void loadFallbackTextureImage() {
        final ResourceLocation emptyLocation = Textures.EMPTY;
        try {
            this.fallbackTextureImage = new TextureImage(emptyLocation);
            this.fallbackTextures.put(emptyLocation, this.fallbackTextureImage);
        }
        catch (final IOException exception) {
            final ClassLoader loader = PlatformEnvironment.getPlatformClassloader().getPlatformClassloader();
            try {
                this.fallbackTextureImage = new TextureImage(loader.getResourceAsStream(String.format(Locale.ROOT, "assets/%s/%s", emptyLocation.getNamespace(), emptyLocation.getPath())), "png");
                this.fallbackTextures.put(emptyLocation, this.fallbackTextureImage);
            }
            catch (final IOException e) {
                this.fallbackTextureImage = null;
            }
        }
    }
    
    protected TextureImage createFallbackTextureImage(final ResourceLocation location) {
        try {
            if (location == null) {
                return this.useFallbackTextureImage();
            }
            TextureImage textureImage = this.fallbackTextures.get(location);
            if (textureImage == null) {
                textureImage = new TextureImage(location);
            }
            else {
                final GameImage gameImage = textureImage.getGameImage();
                if (gameImage == null || gameImage.isFreed()) {
                    textureImage = new TextureImage(location);
                    this.fallbackTextures.put(location, textureImage);
                }
            }
            this.fallbackTextures.put(location, textureImage);
            return textureImage;
        }
        catch (final IOException exception) {
            return this.useFallbackTextureImage();
        }
    }
    
    @Override
    public boolean executeTextureLoader(final String url, @Nullable final ResourceLocation location, final CompletableTextureImage target) {
        return this.textureLoaderRepository.executeTextureLoader(url, location, target);
    }
    
    @Override
    public void registerTextureLoader(final int priority, final TextureLoader loader) {
        this.textureLoaderRepository.registerTextureLoader(priority, loader);
    }
    
    public void releaseRemoteTexture(final ResourceLocation location) {
        this.remoteTextures.remove(location);
        this.fallbackTextures.remove(location);
    }
    
    public void onShutdown() {
        for (final TextureImage textureImage : this.fallbackTextures.values()) {
            if (textureImage == null) {
                continue;
            }
            textureImage.close();
        }
        this.fallbackTextures.clear();
    }
    
    public void onTick() {
        ThreadSafe.ensureRenderThread();
        if (this.queuedTextureReleases.isEmpty()) {
            return;
        }
        CollectionHelper.removeIf((Collection<Object2LongMap.Entry>)this.queuedTextureReleases.object2LongEntrySet(), entry -> {
            if (entry.getLongValue() <= TimeUtil.getMillis()) {
                this.releaseTexture((ResourceLocation)entry.getKey());
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    public void onTextureBind(final ResourceLocation location) {
        ThreadSafe.ensureRenderThread();
        if (this.queuedTextureReleases.isEmpty()) {
            return;
        }
        this.queuedTextureReleases.removeLong((Object)location);
    }
    
    protected IllegalStateException createSubtypeException(final Object value, final Class<?> cls) {
        return new IllegalStateException(value.getClass().getName() + " is not a subtype of " + cls.getName());
    }
    
    static {
        LOGGER = Logging.create(DefaultAbstractTextureRepository.class);
        TEXTURE_RELEASE_QUEUE_TIME = TimeUnit.SECONDS.toMillis(60L);
    }
    
    // This helper class was generated by Procyon to approximate the behavior of an
    // 'invokedynamic' instruction that it doesn't know how to interpret.
    private static final class ProcyonInvokeDynamicHelper_4
    {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static MethodHandle handle;
        private static volatile int fence;
        
        private static MethodHandle handle() {
            final MethodHandle handle = ProcyonInvokeDynamicHelper_4.handle;
            if (handle != null)
                return handle;
            return ProcyonInvokeDynamicHelper_4.ensureHandle();
        }
        
        private static MethodHandle ensureHandle() {
            ProcyonInvokeDynamicHelper_4.fence = 0;
            MethodHandle handle = ProcyonInvokeDynamicHelper_4.handle;
            if (handle == null) {
                MethodHandles.Lookup lookup = ProcyonInvokeDynamicHelper_4.LOOKUP;
                try {
                    handle = ((CallSite)SwitchBootstraps.typeSwitch(lookup, "typeSwitch", MethodType.methodType(int.class, Object.class, int.class), ThemeResourceLocation.class, AbstractResourceLocation.class, AnimatedResourceLocation.class)).dynamicInvoker();
                }
                catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
                ProcyonInvokeDynamicHelper_4.fence = 1;
                ProcyonInvokeDynamicHelper_4.handle = handle;
                ProcyonInvokeDynamicHelper_4.fence = 0;
            }
            return handle;
        }
        
        private static int invoke(Object p0, int p1) {
            try {
                return ProcyonInvokeDynamicHelper_4.handle().invokeExact(p0, p1);
            }
            catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
}
