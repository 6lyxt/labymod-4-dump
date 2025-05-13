// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main;

import net.labymod.api.util.io.web.request.AbstractRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.FileRequest;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.BuildData;
import net.labymod.core.main.updater.manifest.LabyModManifest;
import java.util.stream.Stream;
import java.io.IOException;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import java.nio.file.Path;

public class AssetLoader
{
    private static final AssetLoader instance;
    private static final Path ASSET_DIRECTORY;
    private static final Logging LOGGER;
    private static final Map<String, LocalAsset> ASSETS;
    
    public static AssetLoader getInstance() {
        return AssetLoader.instance;
    }
    
    public void loadAssets() {
        try {
            if (!Files.exists(AssetLoader.ASSET_DIRECTORY, new LinkOption[0])) {
                Files.createDirectories(AssetLoader.ASSET_DIRECTORY, (FileAttribute<?>[])new FileAttribute[0]);
            }
            else {
                try (final Stream<Path> files = Files.list(AssetLoader.ASSET_DIRECTORY)) {
                    files.forEach(path -> {
                        final LocalAsset asset = LocalAsset.of(path);
                        AssetLoader.ASSETS.put(asset.getName(), asset);
                        if (asset.getName().endsWith(".jar") && asset.isNotCorrupted()) {
                            PlatformEnvironment.getPlatformClassloader().addPath(path);
                        }
                        return;
                    });
                }
            }
        }
        catch (final IOException e) {
            AssetLoader.LOGGER.error("An exception occurred while loading the assets. Cause: {} ({})", e.getClass().getSimpleName(), e.getMessage());
        }
    }
    
    public void downloadAssets(final boolean async, final LabyModManifest.Version version) {
        final String commitReference = BuildData.commitReference();
        if (!version.getCommitReference().equals(commitReference)) {
            AssetLoader.ASSETS.clear();
            return;
        }
        boolean downloaded = false;
        for (Map.Entry<String, String> entry : version.getAssets().entrySet()) {
            final String assetName = entry.getKey();
            final Path path = AssetLoader.ASSET_DIRECTORY.resolve(assetName + ".jar");
            final LocalAsset localAsset = AssetLoader.ASSETS.get(path.getFileName().toString());
            if (localAsset != null && IOUtil.exists(localAsset.getPath()) && localAsset.isNotCorrupted()) {
                continue;
            }
            downloaded = true;
            this.downloadAsset(path, version.getCommitReference(), assetName, entry.getValue(), async);
        }
        if (async && downloaded) {
            Laby.labyAPI().minecraft().executeNextTick(() -> LabyMod.getInstance().minecraft().reloadResources());
        }
        AssetLoader.ASSETS.clear();
    }
    
    protected void downloadAsset(final Path path, final String commitReference, final String assetName, final String assetHash, final boolean async) {
        AssetLoader.LOGGER.info("Downloading {}...", path);
        final Request<Path> request = ((AbstractRequest<T, Request<Path>>)((AbstractRequest<T, FileRequest>)((AbstractRequest<T, FileRequest>)((AbstractRequest<T, FileRequest>)Request.ofFile(path)).url(Constants.Urls.ASSET_DOWNLOAD, new Object[] { BuildData.releaseType(), commitReference, assetName, assetHash })).addHeader("Release-Channel", (Object)BuildData.releaseType())).userAgent("LabyMod4")).async(async);
        DefaultWebResolver.instance().resolveConnection(request, response -> {
            if (response.hasException()) {
                AssetLoader.LOGGER.warn("Could not download asset {}. Cause: {}", assetName, response.exception().getMessage());
            }
            else {
                PlatformEnvironment.getPlatformClassloader().addPath(path);
            }
        });
    }
    
    static {
        instance = new AssetLoader();
        ASSET_DIRECTORY = Constants.Files.fromArguments("net.labymod.assets-dir", Constants.Files.LABYMOD_DIRECTORY.resolve("assets"));
        LOGGER = DefaultLoggingFactory.createLogger(AssetLoader.class);
        ASSETS = new HashMap<String, LocalAsset>();
    }
    
    static class LocalAsset
    {
        private final String name;
        private final Path path;
        private final boolean corrupted;
        
        private LocalAsset(@NotNull final Path path) {
            this.path = path;
            this.name = path.getFileName().toString();
            this.corrupted = IOUtil.isCorrupted(path);
            if (this.corrupted) {
                AssetLoader.LOGGER.warn("Corrupted file \"{}\" was found.", path);
            }
        }
        
        @Contract("_ -> new")
        @NotNull
        public static LocalAsset of(final Path path) {
            return new LocalAsset(path);
        }
        
        public String getName() {
            return this.name;
        }
        
        public Path getPath() {
            return this.path;
        }
        
        public boolean isNotCorrupted() {
            return !this.corrupted;
        }
    }
}
