// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import net.labymod.api.util.gson.AddonEntrypointTypeAdapter;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import java.lang.reflect.Type;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.api.models.version.VersionCompatibility;
import com.google.gson.GsonBuilder;
import java.util.zip.ZipEntry;
import java.io.IOException;
import net.labymod.api.util.HashUtil;
import java.nio.file.Files;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.io.zip.ZipException;
import net.labymod.core.addon.loader.AddonValidator;
import com.google.gson.JsonElement;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.util.io.zip.Zips;
import net.labymod.api.property.Property;
import java.nio.file.Path;
import net.labymod.api.Constants;
import java.util.Iterator;
import net.labymod.core.addon.loader.verify.UnusedDependencyVerifier;
import net.labymod.core.addon.loader.verify.AddonDependencyVerifier;
import net.labymod.core.addon.loader.verify.AddonCompatibilityVerifier;
import net.labymod.core.addon.loader.verify.AddonSortingVerifier;
import net.labymod.core.addon.loader.download.AddonDependencyDownloader;
import net.labymod.core.addon.loader.download.DefaultAddonDownloader;
import net.labymod.core.addon.loader.download.AdditionalAddonDownloader;
import net.labymod.core.addon.loader.update.AddonUpdater;
import net.labymod.core.addon.loader.initial.AdditionalAddonLoader;
import net.labymod.core.addon.loader.initial.InstalledAddonLoader;
import java.util.Comparator;
import java.util.ArrayList;
import net.labymod.core.main.LibraryLoader;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.loader.verify.IncompatibleAddonVerifier;
import net.labymod.core.addon.loader.initial.ClasspathAddonLoader;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import com.google.gson.Gson;
import net.labymod.api.service.Service;

public class AddonLoader extends Service
{
    public static final Gson GSON;
    private static final Logging LOGGER;
    private final DefaultAddonService addonService;
    private final List<AddonLoaderSubService> subServices;
    private final AddonPreparer addonPreparer;
    private final ClasspathAddonLoader classpathAddonLoader;
    private final IncompatibleAddonVerifier incompatibleAddonVerifier;
    private final List<InstalledAddonInfo> addonsToBeLoaded;
    private LibraryLoader libraryLoader;
    
    public AddonLoader(final DefaultAddonService addonService) {
        this.addonService = addonService;
        this.subServices = new ArrayList<AddonLoaderSubService>();
        this.addonsToBeLoaded = new ArrayList<InstalledAddonInfo>();
        this.addonPreparer = new AddonPreparer(this, this.addonService);
        this.classpathAddonLoader = new ClasspathAddonLoader(this);
        this.incompatibleAddonVerifier = new IncompatibleAddonVerifier(this);
        this.initializeSubServices();
        this.subServices.sort(Comparator.comparingInt(a -> a.stage().ordinal()));
    }
    
    public LibraryLoader libraryLoader() {
        if (this.libraryLoader == null) {
            this.libraryLoader = new LibraryLoader();
        }
        return this.libraryLoader;
    }
    
    private void initializeSubServices() {
        this.subServices.add(this.classpathAddonLoader);
        this.subServices.add(new InstalledAddonLoader(this));
        final AdditionalAddonLoader additionalAddonLoader = new AdditionalAddonLoader(this);
        this.subServices.add(additionalAddonLoader);
        this.subServices.add(new AddonUpdater(this));
        this.subServices.add(new AdditionalAddonDownloader(this, additionalAddonLoader));
        this.subServices.add(new DefaultAddonDownloader(this));
        this.subServices.add(new AddonDependencyDownloader(this));
        this.subServices.add(new AddonSortingVerifier(this));
        this.subServices.add(new AddonCompatibilityVerifier(this));
        this.subServices.add(this.incompatibleAddonVerifier);
        this.subServices.add(new AddonDependencyVerifier(this));
        this.subServices.add(new UnusedDependencyVerifier(this, this.incompatibleAddonVerifier));
        this.subServices.add(this.addonPreparer);
    }
    
    @Override
    protected void prepare() {
        this.forEachSubService(AddonLoaderSubService.SubServiceStage.INITIAL, this.addonsToBeLoaded);
        this.forEachSubService(AddonLoaderSubService.SubServiceStage.UPDATE, this.addonsToBeLoaded);
        this.forEachSubService(AddonLoaderSubService.SubServiceStage.DOWNLOAD, this.addonsToBeLoaded);
        this.forEachSubService(AddonLoaderSubService.SubServiceStage.VERIFY, this.addonsToBeLoaded);
        this.forEachSubService(AddonLoaderSubService.SubServiceStage.PREPARE, this.addonsToBeLoaded);
        for (AddonLoaderSubService subService : this.subServices) {
            try {
                subService.completed();
            }
            catch (final Exception exception) {
                AddonLoader.LOGGER.error("An exception occurred while completing sub addon loader " + subService.getClass().getSimpleName(), (Throwable)exception);
            }
        }
    }
    
    public AddonPreparer addonPreparer() {
        return this.addonPreparer;
    }
    
    public ClasspathAddonLoader classpathAddonLoader() {
        return this.classpathAddonLoader;
    }
    
    public boolean isAdditionalAddon(final InstalledAddonInfo addonInfo) {
        if (addonInfo == null) {
            return false;
        }
        final String directory = addonInfo.getDirectory();
        return directory != null && !directory.equals(Constants.Files.ADDONS.toString());
    }
    
    @NotNull
    public InstalledAddonInfo loadAddonInfo(final Path path) throws ZipException {
        final Property<byte[]> addonInfoBytesProperty = new Property<byte[]>(null);
        Zips.read(path, (entry, bytes) -> {
            if (!entry.getName().equals("addon.json")) {
                return Boolean.valueOf(false);
            }
            else {
                addonInfoBytesProperty.set(bytes);
                return Boolean.valueOf(true);
            }
        });
        final byte[] addonInfoBytes = addonInfoBytesProperty.get();
        if (addonInfoBytes == null) {
            throw new AddonLoadException("No addon.json found in " + String.valueOf(path));
        }
        final JsonObject object = (JsonObject)AddonLoader.GSON.fromJson(new String(addonInfoBytes, StandardCharsets.UTF_8), (Class)JsonObject.class);
        if (object == null) {
            throw new AddonLoadException("Invalid addon.json: not a json object");
        }
        object.addProperty("fileName", path.getFileName().toString());
        object.addProperty("directory", path.getParent().toString());
        try {
            object.addProperty("fileHash", this.getSHA1Hash(path));
        }
        catch (final Exception e) {
            AddonLoader.LOGGER.warn("Could not calculate the hash of the addon " + String.valueOf(path), (Throwable)e);
        }
        final InstalledAddonInfo addonInfo = (InstalledAddonInfo)AddonLoader.GSON.fromJson((JsonElement)object, (Class)InstalledAddonInfo.class);
        AddonValidator.validateAddonInfo(addonInfo);
        return addonInfo;
    }
    
    public boolean isAddonInList(final List<InstalledAddonInfo> list, final String namespace) {
        return CollectionHelper.anyMatch(list, addon -> addon.getNamespace().equals(namespace));
    }
    
    public String getSHA1Hash(final Path path) throws IOException {
        return HashUtil.sha1Hex(Files.readAllBytes(path));
    }
    
    private void forEachSubService(final AddonLoaderSubService.SubServiceStage stage, final List<InstalledAddonInfo> addons) {
        for (AddonLoaderSubService subService : this.subServices) {
            if (subService.stage() == stage) {
                try {
                    AddonLoader.LOGGER.info("Executing sub addon loader " + subService.getClass().getSimpleName() + " with stage " + String.valueOf(stage), new Object[0]);
                    subService.setAddons(addons);
                    subService.handle();
                }
                catch (final Exception exception) {
                    AddonLoader.LOGGER.error("An exception occurred while executing sub addon loader " + subService.getClass().getSimpleName(), (Throwable)exception);
                }
            }
        }
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)VersionCompatibility.class, (Object)new VersionCompatibilityDeserializer()).registerTypeAdapter((Type)AddonEntrypoint.class, (Object)new AddonEntrypointTypeAdapter()).create();
        LOGGER = Logging.create(AddonLoader.class);
    }
}
