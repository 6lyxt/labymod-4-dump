// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.BuildData;
import java.net.URLClassLoader;
import java.io.IOException;
import java.io.InputStream;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.mixin.Mixins;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.service.Service;
import java.util.function.Function;
import net.labymod.api.mixin.dynamic.DynamicMixinUtil;
import net.labymod.api.util.io.IOUtil;
import java.util.Collection;
import net.labymod.core.loader.isolated.IsolatedLibraryLoader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.isolated.jna.JNALoader;
import net.labymod.core.loader.isolated.lwjgl.ThinLWJGL;
import net.labymod.core.loader.isolated.lwjgl.LWJGL2Filter;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;
import net.labymod.core.loader.isolated.lwjgl.LWJGL3Preloader;
import net.labymod.core.loader.isolated.IsolatedLibraryPredicate;
import net.labymod.core.loader.isolated.lwjgl.LWJGL3NativeFilter;
import net.labymod.core.loader.isolated.ManifestIsolatedLibraryLoader;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Iterator;
import net.labymod.core.main.AssetLoader;
import net.labymod.api.modloader.mod.ModInfo;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.util.KeyValue;
import net.labymod.api.Laby;
import com.google.gson.JsonElement;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.DefaultAddonService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.api.util.version.serial.VersionDeserializer;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.util.crashtracker.CrashTracker;
import java.net.URL;
import java.util.Map;
import net.labymod.api.models.version.Version;
import java.util.UUID;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import java.io.PrintStream;
import net.labymod.api.loader.LabyModLoader;

public class DefaultLabyModLoader implements LabyModLoader
{
    private static final String LABYMOD_REFMAP_SUFFIX = "labymod4.refmap.json";
    public static final PrintStream SYSOUT;
    private static final Logging LOGGER;
    private static LabyModLoader instance;
    private List<String> arguments;
    private Path gameDirectory;
    private Path assetsDirectory;
    private String profile;
    private String rawUuid;
    private UUID uuid;
    private boolean labyModDevelopmentEnvironment;
    private boolean addonDevelopmentEnvironment;
    private Version version;
    private String effectiveReleaseChannel;
    private final Map<String, URL> mixinResourceSources;
    private final List<CrashTracker.MixinConfig> mixinConfigs;
    
    public DefaultLabyModLoader() {
        this.mixinResourceSources = new HashMap<String, URL>();
        this.mixinConfigs = new ArrayList<CrashTracker.MixinConfig>();
    }
    
    public static LabyModLoader getInstance() {
        if (DefaultLabyModLoader.instance == null) {
            DefaultLabyModLoader.instance = new DefaultLabyModLoader();
        }
        return DefaultLabyModLoader.instance;
    }
    
    @Override
    public void gameOptions(final List<String> arguments, final Path gameDirectory, final Path assetsDirectory, final String profile, final boolean labyModDevEnvironment, final boolean addonDevEnvironment, final String version) {
        this.arguments = arguments;
        this.gameDirectory = gameDirectory;
        this.assetsDirectory = assetsDirectory;
        this.profile = profile;
        this.labyModDevelopmentEnvironment = labyModDevEnvironment;
        this.addonDevelopmentEnvironment = addonDevEnvironment;
        this.version = VersionDeserializer.from(version);
        this.customizedArguments();
        CrashTracker.setAdditionalDataSupplier(() -> {
            final JsonObject jsonObject = new JsonObject();
            final JsonArray installedAddons = new JsonArray();
            DefaultAddonService.getInstance().getLoadedAddons().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final LoadedAddon loadedAddon = iterator.next();
                final InstalledAddonInfo info = loadedAddon.info();
                if (info == null) {
                    continue;
                }
                else {
                    final JsonObject addonObject = new JsonObject();
                    addonObject.addProperty("namespace", info.getNamespace());
                    addonObject.addProperty("version", info.getVersion());
                    addonObject.addProperty("local", Boolean.valueOf(!info.isFlintAddon()));
                    installedAddons.add((JsonElement)addonObject);
                }
            }
            if (installedAddons.size() != 0) {
                jsonObject.add("installedAddons", (JsonElement)installedAddons);
            }
            final JsonArray installedMods = new JsonArray();
            Laby.references().modLoaderRegistry().getElements().iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final KeyValue<ModLoader> element = iterator2.next();
                final ModLoader loader = element.getValue();
                if (loader == null) {
                    continue;
                }
                else {
                    loader.getModInfos().iterator();
                    final Iterator iterator3;
                    while (iterator3.hasNext()) {
                        final ModInfo info2 = iterator3.next();
                        final JsonObject modObject = new JsonObject();
                        modObject.addProperty("modId", info2.getId());
                        modObject.addProperty("version", info2.version().toString());
                        modObject.addProperty("loader", loader.getId());
                        installedMods.add((JsonElement)modObject);
                    }
                }
            }
            if (installedMods.size() != 0) {
                jsonObject.add("installedMods", (JsonElement)installedMods);
            }
            return jsonObject.toString();
        });
        if (!this.labyModDevelopmentEnvironment) {
            AssetLoader.getInstance().loadAssets();
        }
    }
    
    @Override
    public void loadIsolatedLibraries() {
        if (this.labyModDevelopmentEnvironment) {
            return;
        }
        final IsolatedLibraryLoader isolatedLibraryLoader = new ManifestIsolatedLibraryLoader();
        isolatedLibraryLoader.addFilter(new LWJGL3NativeFilter());
        isolatedLibraryLoader.addFinishHandler(new LWJGL3Preloader());
        isolatedLibraryLoader.addFilter(new LWJGL2Filter());
        isolatedLibraryLoader.addFinishHandler(new ThinLWJGL());
        isolatedLibraryLoader.addFinishHandler(new JNALoader());
        isolatedLibraryLoader.load(PlatformEnvironment.getPlatformClassloader().getPlatformClassloader());
    }
    
    private void customizedArguments() {
        final List<String> customizedArguments = new ArrayList<String>(this.arguments);
        customizedArguments.add("--gameDir");
        customizedArguments.add(IOUtil.toFile(this.gameDirectory).getAbsolutePath());
        customizedArguments.add("--assetsDir");
        customizedArguments.add(IOUtil.toFile(this.assetsDirectory).getAbsolutePath());
        customizedArguments.add("--version");
        customizedArguments.add(this.version.toString());
        this.arguments = customizedArguments;
    }
    
    @Override
    public void loadAddons() {
        DynamicMixinUtil.setLoadResources(PlatformEnvironment::getResources);
        CrashTracker.setAddonNamespaceFunction(cls -> {
            try {
                final ClassLoader classLoader = cls.getClassLoader();
                return (String)DefaultAddonService.getInstance().getAddon(classLoader).map((Function<? super LoadedAddon, ?>)LoadedAddon::info).map((Function<? super Object, ? extends String>)InstalledAddonInfo::getNamespace).orElse(null);
            }
            catch (final Exception ignored) {
                return null;
            }
        });
        CrashTracker.setAddonExistsFunction(namespace -> DefaultAddonService.getInstance().getAddonInfo(namespace) != null);
        CrashTracker.setMixinConfigSupplier(() -> this.mixinConfigs);
        ((Service)MappingService.instance()).prepareSynchronously();
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        addonService.prepareSynchronously();
        for (final Config config : Mixins.getConfigs()) {
            final CrashTracker.MixinConfig mixinConfig = this.convertMixinConfig(config);
            if (mixinConfig == null) {
                continue;
            }
            this.mixinConfigs.add(mixinConfig);
        }
        final Collection<LoadedAddon> addons = addonService.getLoadedAddons();
        if (addons.isEmpty()) {
            return;
        }
        addons.forEach(addon -> addon.getTransformers().forEach(LoadedAddonClassTransformer::init));
    }
    
    private CrashTracker.MixinConfig convertMixinConfig(final Config config) {
        if (config == null) {
            return null;
        }
        final String mixinName = config.getName();
        String refMapName = mixinName.replace(".mixins.json", ".refmap.json");
        final IMixinConfig mixinConfig = config.getConfig();
        try {
            final Object refMapperConfig = mixinConfig.getClass().getField("refMapperConfig").get(mixinConfig);
            if (refMapperConfig instanceof final String s) {
                refMapName = s;
            }
        }
        catch (final Exception ex) {}
        String modId = null;
        if (mixinConfig.hasDecoration("fabric-modId")) {
            modId = (String)mixinConfig.getDecoration("fabric-modId");
        }
        else if (mixinConfig.hasDecoration("labymod-namespace")) {
            modId = (String)mixinConfig.getDecoration("labymod-namespace");
        }
        return new CrashTracker.MixinConfig(mixinName, refMapName, modId, this.convertMixinConfig(config.getParent()));
    }
    
    @Override
    public InputStream getMixinServiceResourceAsStream(final String name, final InputStream parentInputStream) {
        if (this.addonDevelopmentEnvironment && name.endsWith("labymod4.refmap.json")) {
            return null;
        }
        final URL mixinResourceSource = this.mixinResourceSources.get(name);
        if (mixinResourceSource != null) {
            try {
                return mixinResourceSource.openStream();
            }
            catch (final IOException ex) {}
        }
        for (final LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            final InputStream stream = addon.getClassLoader().getResourceAsStream(name);
            if (stream != null) {
                return stream;
            }
        }
        return parentInputStream;
    }
    
    @Override
    public byte[] getMixinServiceClassBytes(final String name, final String transformedName, final byte[] classBytes) throws IOException {
        if (classBytes != null) {
            return classBytes;
        }
        for (final LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
            final URLClassLoader appClassLoader = (URLClassLoader)addon.getClassLoader();
            InputStream classStream = null;
            try {
                final String resourcePath = transformedName.replace('.', '/').concat(".class");
                classStream = appClassLoader.getResourceAsStream(resourcePath);
                if (classStream != null) {
                    return IOUtil.readBytes(classStream);
                }
                continue;
            }
            catch (final Exception ex) {}
            finally {
                IOUtil.closeSilent(classStream);
            }
        }
        return classBytes;
    }
    
    @Override
    public void registerMixinResourceSource(final String name, final URL url) {
        this.mixinResourceSources.put(name, url);
    }
    
    @Override
    public Version version() {
        return this.version;
    }
    
    @Override
    public List<String> getArguments() {
        return this.arguments;
    }
    
    @Override
    public Path getGameDirectory() {
        return this.gameDirectory;
    }
    
    @Override
    public Path getAssetsDirectory() {
        return this.assetsDirectory;
    }
    
    @Override
    public String getProfile() {
        return this.profile;
    }
    
    @Override
    public String getEffectiveReleaseChannel() {
        if (this.effectiveReleaseChannel == null) {
            if (this.isAddonDevelopmentEnvironment()) {
                this.effectiveReleaseChannel = "production";
            }
            else {
                final String currentReleaseChannel = BuildData.releaseType();
                if (currentReleaseChannel == null) {
                    this.effectiveReleaseChannel = "production";
                }
                else {
                    this.effectiveReleaseChannel = currentReleaseChannel;
                }
            }
        }
        return this.effectiveReleaseChannel;
    }
    
    @Override
    public boolean isLabyModDevelopmentEnvironment() {
        return this.labyModDevelopmentEnvironment;
    }
    
    @Override
    public boolean isAddonDevelopmentEnvironment() {
        return this.addonDevelopmentEnvironment;
    }
    
    public String getRawUniqueId() {
        return this.rawUuid;
    }
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    private boolean hasArgument(final String argumentKey) {
        return this.arguments.stream().anyMatch(argument -> argument.equalsIgnoreCase(argumentKey));
    }
    
    static {
        SYSOUT = System.out;
        LOGGER = DefaultLoggingFactory.createLogger("LabyModLoader");
    }
}
