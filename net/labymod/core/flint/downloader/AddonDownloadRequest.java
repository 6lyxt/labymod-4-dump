// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.downloader;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.client.session.Session;
import java.util.UUID;
import net.labymod.api.util.io.web.request.FormData;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.StringRequest;
import java.nio.file.FileAlreadyExistsException;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import java.util.Objects;
import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.core.main.LabyMod;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.CopyOption;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import java.util.Collection;
import net.labymod.api.Constants;
import net.labymod.core.flint.marketplace.FlintModification;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import java.nio.file.Path;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.core.flint.index.FlintIndexLoader;
import net.labymod.api.util.logging.Logging;
import java.util.List;
import net.labymod.api.util.io.web.request.types.AbstractFileRequest;

public class AddonDownloadRequest extends AbstractFileRequest<AddonDownloadResult, AddonDownloadRequest>
{
    public static final List<String> UNSUPPORTED_ADDONS;
    private static final Logging LOGGER;
    private static final FlintIndexLoader INDEX_LOADER;
    private static final DefaultWebResolver WEB_RESOLVER;
    private static final Supplier<Object> SESSION_SUPPLIER;
    private static final BiPredicate<String, InstalledAddonInfo> DEFAULT_EXISTS_CALLBACK;
    private BiPredicate<String, InstalledAddonInfo> existsCallback;
    private String namespace;
    private String hash;
    private Path finalPath;
    private boolean downloadDependencies;
    private boolean downloadOptionalDependencies;
    private List<String> skippedDependencies;
    private boolean loadUniqueIdFromLoader;
    private BiPredicate<String, InstalledAddonInfo> dependencyExistsCallback;
    private boolean ignoreUnsupported;
    private boolean throwNotInIndexException;
    private boolean skippedDownload;
    private InstalledAddonInfo currentAddonInfo;
    
    private AddonDownloadRequest() {
        super(null, null);
        this.existsCallback = AddonDownloadRequest.DEFAULT_EXISTS_CALLBACK;
        this.skippedDependencies = List.of();
        this.skippedDownload = false;
    }
    
    public static AddonDownloadRequest create() {
        return new AddonDownloadRequest();
    }
    
    public AddonDownloadRequest namespace(final String namespace) throws IllegalArgumentException {
        this.setNamespace(namespace);
        final JsonObject modificationObject = AddonDownloadRequest.INDEX_LOADER.getModificationObject(namespace);
        if (modificationObject == null) {
            throw new IllegalArgumentException("No modification with namespace " + namespace + " found!");
        }
        return this.pathIfUnset(namespace + ".jar");
    }
    
    public AddonDownloadRequest throwNotInIndexException() {
        this.throwNotInIndexException = true;
        return this;
    }
    
    public AddonDownloadRequest namespace(final InstalledAddonInfo addonInfo) {
        return this.namespace(addonInfo.getNamespace());
    }
    
    public AddonDownloadRequest namespace(final FlintModification modification) {
        this.setNamespace(modification.getNamespace());
        return this.pathIfUnset(this.namespace + ".jar");
    }
    
    private AddonDownloadRequest pathIfUnset(final String fileName) {
        if (this.path == null) {
            this.path(fileName);
        }
        return this;
    }
    
    public AddonDownloadRequest path(final Path directory, final String fileName) {
        this.finalPath = directory.resolve(fileName);
        this.path = directory.resolve(fileName + ".download");
        return this;
    }
    
    public AddonDownloadRequest path(final String fileName) {
        return this.path(Constants.Files.ADDONS, fileName);
    }
    
    public AddonDownloadRequest hash(String hash) {
        if (hash != null && hash.isEmpty()) {
            hash = null;
        }
        if ((this.hash = hash) != null) {
            this.url("https://flintmc.net/api/client-store/fetch-jar-by-hash/%s", new Object[] { hash });
        }
        return this;
    }
    
    public AddonDownloadRequest percentageConsumer(final Consumer<Double> percentageConsumer) {
        this.percentageConsumer = percentageConsumer;
        return this;
    }
    
    public AddonDownloadRequest existsStrategy(final BiPredicate<String, InstalledAddonInfo> existsCallback) {
        this.existsCallback = existsCallback;
        return this;
    }
    
    public AddonDownloadRequest downloadDependencies(final boolean downloadDependencies, final BiPredicate<String, InstalledAddonInfo> dependencyExistsCallback) {
        this.downloadDependencies = downloadDependencies;
        if (dependencyExistsCallback == null) {
            this.dependencyExistsCallback = AddonDownloadRequest.DEFAULT_EXISTS_CALLBACK;
        }
        else {
            this.dependencyExistsCallback = dependencyExistsCallback;
        }
        return this;
    }
    
    public AddonDownloadRequest downloadDependencies(final boolean downloadDependencies) {
        return this.downloadDependencies(downloadDependencies, null);
    }
    
    public AddonDownloadRequest downloadOptionalDependencies(final boolean downloadOptionalDependencies) {
        if (!this.downloadDependencies && downloadOptionalDependencies) {
            this.downloadDependencies = true;
        }
        this.downloadOptionalDependencies = downloadOptionalDependencies;
        return this;
    }
    
    public AddonDownloadRequest skipDependencies(final List<String> skippedDependencies) {
        this.skippedDependencies = List.copyOf((Collection<? extends String>)skippedDependencies);
        return this;
    }
    
    public AddonDownloadRequest skipDependencies(final String... skippedDependencies) {
        return this.skipDependencies(List.of(skippedDependencies));
    }
    
    public AddonDownloadRequest loadUniqueIdFromLoader() {
        this.loadUniqueIdFromLoader = true;
        return this;
    }
    
    public AddonDownloadRequest ignoreUnsupported() {
        return this.ignoreUnsupported(true);
    }
    
    public AddonDownloadRequest ignoreUnsupported(final boolean ignoreUnsupported) {
        this.ignoreUnsupported = ignoreUnsupported;
        return this;
    }
    
    @Override
    protected AddonDownloadRequest prepareCopy() {
        final AddonDownloadRequest request = new AddonDownloadRequest();
        request.namespace = this.namespace;
        request.hash = this.hash;
        request.finalPath = this.finalPath;
        request.existsCallback = this.existsCallback;
        request.downloadDependencies = this.downloadDependencies;
        request.dependencyExistsCallback = this.dependencyExistsCallback;
        request.loadUniqueIdFromLoader = this.loadUniqueIdFromLoader;
        request.ignoreUnsupported = this.ignoreUnsupported;
        return this.applyRequestDataTo(request);
    }
    
    @Override
    protected AddonDownloadResult handle(final Response<AddonDownloadResult> result, final WebInputStream inputStream) throws IOException {
        if (this.namespace == null) {
            throw new IllegalStateException("Namespace cannot be null");
        }
        final boolean unsupported = !this.ignoreUnsupported && AddonDownloadRequest.UNSUPPORTED_ADDONS.contains(this.namespace);
        Path downloadPath;
        if (unsupported) {
            downloadPath = null;
        }
        else {
            downloadPath = ((AbstractFileRequest<AddonDownloadResult, R>)this).download(result, inputStream);
        }
        final AddonDownloadResult addonDownloadResult = new AddonDownloadResult(this.skippedDownload);
        if (!unsupported && !this.skippedDownload) {
            if (this.finalPath != null) {
                Files.move(downloadPath, this.finalPath, StandardCopyOption.REPLACE_EXISTING);
            }
            this.loadAddonInfo((this.finalPath == null) ? this.path : this.finalPath);
            addonDownloadResult.addAddonInfo(this.currentAddonInfo);
            final Path path = (this.finalPath == null) ? this.path : this.finalPath;
            if (path.getParent().equals(Constants.Files.ADDONS)) {
                LabyMod.getInstance().sendLauncherPacket(new LauncherAddonInstalledPacket(this.currentAddonInfo.getNamespace(), path.getFileName().toString()));
            }
            this.sendDownloadCount(this.namespace);
        }
        else {
            addonDownloadResult.addAddonInfo(this.currentAddonInfo);
        }
        if (!this.downloadDependencies) {
            return addonDownloadResult;
        }
        if (this.currentAddonInfo != null) {
            final DefaultAddonService addonService = DefaultAddonService.getInstance();
            for (final AddonDependency addonDependency : this.currentAddonInfo.getCompatibleAddonDependencies(Laby.labyAPI().labyModLoader().version())) {
                final String namespace = addonDependency.getNamespace();
                Label_0506: {
                    if ((!addonDependency.isOptional() || this.downloadOptionalDependencies) && !this.skippedDependencies.contains(namespace)) {
                        if (!addonService.getAddon(namespace).isPresent()) {
                            String hash = null;
                            final FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
                            final FlintModification modification = flintIndex.getModification(namespace);
                            if (modification != null) {
                                hash = modification.getFileHash();
                            }
                            Response<AddonDownloadResult> response;
                            try {
                                response = new AddonDownloadRequest().hash(hash).namespace(namespace).existsStrategy(this.dependencyExistsCallback).downloadDependencies(true, this.dependencyExistsCallback).downloadOptionalDependencies(this.downloadOptionalDependencies).skipDependencies(this.skippedDependencies).ignoreUnsupported(this.ignoreUnsupported).executeSync();
                            }
                            catch (final Exception e) {
                                if (this.throwNotInIndexException) {
                                    throw e;
                                }
                                break Label_0506;
                            }
                            if (response.hasException()) {
                                throw new IOException(response.exception());
                            }
                            final Response<AddonDownloadResult> response2 = response;
                            final AddonDownloadResult obj = addonDownloadResult;
                            Objects.requireNonNull(obj);
                            response2.ifPresent(obj::addonDownloadResult);
                        }
                    }
                }
            }
        }
        return addonDownloadResult;
    }
    
    private InstalledAddonInfo loadAddonInfo(final Path path) {
        final AddonLoader addonLoader = DefaultAddonService.getInstance().addonLoader();
        try {
            this.currentAddonInfo = addonLoader.loadAddonInfo(path);
        }
        catch (final Exception exception) {
            AddonDownloadRequest.LOGGER.error("Could not load addon info from {}", path, exception);
            this.currentAddonInfo = null;
        }
        return this.currentAddonInfo;
    }
    
    @Override
    protected boolean continueDownload() throws IOException {
        this.skippedDownload = false;
        this.currentAddonInfo = null;
        final Path path = (this.finalPath == null) ? this.path : this.finalPath;
        if (!IOUtil.exists(path)) {
            return true;
        }
        this.loadAddonInfo(path);
        if (this.currentAddonInfo == null) {
            return true;
        }
        if (this.existsCallback == null) {
            throw new FileAlreadyExistsException("File already exists but no strategy callback is set!");
        }
        final boolean continueDownload = this.existsCallback.test(this.namespace, this.currentAddonInfo);
        this.skippedDownload = !continueDownload;
        return continueDownload;
    }
    
    private void sendDownloadCount(final String namespace) {
        final UUID uniqueId = this.getUniqueId();
        if (uniqueId == null) {
            return;
        }
        final StringRequest request = ((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)Request.ofString()).method(Request.Method.POST)).url("https://flintmc.net/api/client-store/add-download-count/%s", new Object[] { namespace })).form(new FormData[] { FormData.builder().name("uuid").value(uniqueId.toString()).build() })).async();
        AddonDownloadRequest.WEB_RESOLVER.resolveConnection((Request<Object>)request, response -> {});
    }
    
    private UUID getUniqueId() {
        if (!this.loadUniqueIdFromLoader) {
            final Object sessionObject = AddonDownloadRequest.SESSION_SUPPLIER.get();
            if (!(sessionObject instanceof Session)) {
                return null;
            }
            final Session session = (Session)sessionObject;
            if (!session.isPremium() || !session.hasUniqueId()) {
                return null;
            }
            return session.getUniqueId();
        }
        else {
            final DefaultLabyModLoader instance = (DefaultLabyModLoader)DefaultLabyModLoader.getInstance();
            if (instance.getRawUniqueId() == null) {
                return null;
            }
            final String uniqueId = instance.getRawUniqueId();
            if (uniqueId.contains("-")) {
                return UUID.fromString(uniqueId);
            }
            return UUIDTypeAdapter.fromString(uniqueId);
        }
    }
    
    private void setNamespace(final String namespace) {
        this.namespace = namespace;
        if (this.hash == null) {
            final FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
            final FlintModification modification = flintIndex.getModification(namespace);
            if (modification != null) {
                this.hash(modification.getFileHash());
            }
        }
        if (this.url == null) {
            this.url("https://flintmc.net/api/client-store/fetch-jar/%s/%s", new Object[] { namespace, DefaultLabyModLoader.getInstance().version().toString() });
        }
    }
    
    @Override
    public Response<AddonDownloadResult> executeSync() {
        return AddonDownloadRequest.WEB_RESOLVER.resolveConnection((Request<AddonDownloadResult>)this);
    }
    
    @Override
    public void execute(final Callback<AddonDownloadResult> callback) {
        AddonDownloadRequest.WEB_RESOLVER.resolveConnection(this, callback);
    }
    
    static {
        LOGGER = Logging.getLogger();
        INDEX_LOADER = FlintIndexLoader.getInstance();
        WEB_RESOLVER = DefaultWebResolver.instance();
        SESSION_SUPPLIER = (() -> {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            if (minecraft == null || minecraft.sessionAccessor() == null) {
                return null;
            }
            else {
                return minecraft.sessionAccessor().getSession();
            }
        });
        DEFAULT_EXISTS_CALLBACK = ((existingNamespace, info) -> {
            if (info.getNamespace().equals(existingNamespace)) {
                return false;
            }
            else {
                throw new IllegalStateException("Addon " + info.getNamespace() + " is on the same path as " + existingNamespace + " (" + info.getFileName());
            }
        });
        final String unsupportedAddons = System.getProperty("net.labymod.unsupported-addons");
        if (unsupportedAddons == null) {
            UNSUPPORTED_ADDONS = List.of();
        }
        else {
            UNSUPPORTED_ADDONS = List.of(unsupportedAddons.split(","));
        }
    }
    
    public class AddonDownloadResult
    {
        private final List<InstalledAddonInfo> addonInfos;
        private final boolean skippedMainAddon;
        
        private AddonDownloadResult(final AddonDownloadRequest this$0, final boolean skipped) {
            this.addonInfos = new ArrayList<InstalledAddonInfo>();
            this.skippedMainAddon = skipped;
        }
        
        private AddonDownloadResult addAddonInfo(final InstalledAddonInfo addonInfo) {
            this.addonInfos.add(addonInfo);
            return this;
        }
        
        private AddonDownloadResult addonDownloadResult(final AddonDownloadResult result) {
            for (final InstalledAddonInfo addonInfo : result.addonInfos) {
                if (addonInfo == null) {
                    continue;
                }
                boolean found = false;
                for (final InstalledAddonInfo info : this.addonInfos) {
                    if (info.getNamespace().equals(addonInfo.getNamespace())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    continue;
                }
                this.addonInfos.add(0, addonInfo);
            }
            return this;
        }
        
        public List<InstalledAddonInfo> getAddonInfos() {
            return Collections.unmodifiableList((List<? extends InstalledAddonInfo>)this.addonInfos);
        }
        
        public boolean hasSkippedMainAddon() {
            return this.skippedMainAddon;
        }
    }
}
