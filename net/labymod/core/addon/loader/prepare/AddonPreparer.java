// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.prepare;

import net.labymod.core.addon.file.AddonResource;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import java.util.Arrays;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.models.OperatingSystem;
import java.nio.file.Path;
import net.labymod.core.loader.ReflectLabyModLoader;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import net.labymod.core.addon.entrypoint.EntrypointInvoker;
import java.util.Comparator;
import java.util.Optional;
import net.labymod.api.loader.platform.PlatformClassloader;
import java.util.Locale;
import java.util.Collection;
import net.labymod.core.addon.file.AddonResourceFinder;
import java.net.MalformedURLException;
import net.labymod.core.loader.DefaultLabyModLoader;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.addon.transformer.WrappedAddonClassTransformer;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import java.util.ArrayList;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.addon.exception.AddonInitException;
import net.labymod.api.addon.exception.UnsupportedAddonException;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.core.addon.AddonClassLoader;
import java.io.IOException;
import java.util.Iterator;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.HashMap;
import net.labymod.core.main.LabyMod;
import net.labymod.core.addon.AddonLoader;
import java.util.List;
import net.labymod.api.models.addon.annotation.AddonEntryPoint;
import java.util.Map;
import net.labymod.core.addon.AddonRemapper;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonPreparer extends AddonLoaderSubService
{
    private final DefaultAddonService addonService;
    private final LabyModLoader labyModLoader;
    private final AddonRemapper addonRemapper;
    private final Map<AddonEntryPoint.Point, List<WrappedAddonEntrypoint>> addonEntrypoints;
    
    public AddonPreparer(final AddonLoader addonLoader, final DefaultAddonService addonService) {
        super(addonLoader, SubServiceStage.PREPARE);
        this.addonService = addonService;
        this.labyModLoader = LabyMod.getInstance().labyModLoader();
        this.addonRemapper = new AddonRemapper();
        this.addonEntrypoints = new HashMap<AddonEntryPoint.Point, List<WrappedAddonEntrypoint>>();
    }
    
    @Override
    public void handle() throws Exception {
        for (final InstalledAddonInfo addonInfo : this.getAddons()) {
            try {
                this.loadAddon(addonInfo);
                this.logger.info("Loading addon {} v{}", addonInfo.getNamespace(), addonInfo.getVersion());
            }
            catch (final Exception exception) {
                this.logger.warn("Unable to load addon {}. {}: {}", addonInfo.getNamespace(), exception.getClass().getSimpleName(), exception.getMessage(), exception);
            }
        }
    }
    
    @Override
    public void completed() throws Exception {
        this.logger.info("Preparing all Loaded Addons...", new Object[0]);
        this.prepareLoadedAddons();
    }
    
    private void loadAddon(final InstalledAddonInfo addonInfo) throws IOException {
        this.loadAddon(addonInfo, AddonPrepareContext.DEFAULT);
    }
    
    public void loadAddon(final InstalledAddonInfo addonInfo, final AddonPrepareContext context) throws IOException {
        final AddonClassLoader classLoader = this.loadAddonClassLoader(addonInfo, addonInfo.getPath());
        this.prepareAddon(classLoader, context);
    }
    
    public void prepareAddon(final AddonClassLoader addonClassLoader, final AddonPrepareContext context) throws IOException {
        try {
            final boolean onClasspath = context == AddonPrepareContext.CLASSPATH && this.labyModLoader.isAddonDevelopmentEnvironment();
            final LoadedAddon loadedAddon = this.initAddon(addonClassLoader, onClasspath);
            if (onClasspath) {
                final InstalledAddonInfo addonInfo = addonClassLoader.getAddonInfo();
                if (addonInfo != null) {
                    this.addonService.setClassPathAddonNamespace(addonInfo.getNamespace());
                }
            }
            this.addonService.addLoadedAddon(loadedAddon, context == AddonPrepareContext.RUNTIME);
        }
        catch (final AddonLoadException | UnsupportedAddonException exception) {
            this.logger.error("Could not load addon", exception);
        }
        catch (final AddonInitException exception2) {
            this.logger.error("Could not initialize addon", exception2);
            try (addonClassLoader) {}
        }
    }
    
    public LoadedAddon initAddon(final AddonClassLoader classLoader, final boolean onClasspath) {
        final InstalledAddonInfo addonInfo = classLoader.getAddonInfo();
        final String mainClassName = addonInfo.getMainClass();
        if (mainClassName == null) {
            throw new AddonInitException("No main class found");
        }
        Class<?> mainClass = null;
        try {
            mainClass = classLoader.loadClass(mainClassName);
        }
        catch (final ClassNotFoundException | LinkageError exception) {
            throw new AddonInitException("Failed to load main class " + String.valueOf(mainClass), exception);
        }
        final List<LoadedAddonClassTransformer> transformers = new ArrayList<LoadedAddonClassTransformer>();
        if (addonInfo.getTransformers() != null) {
            for (final String transformer : addonInfo.getTransformers()) {
                transformers.add(this.parseTransformer(classLoader, transformer));
            }
        }
        return new LoadedAddon(classLoader, mainClass, transformers, this.loadMixinConfigs(classLoader, addonInfo, onClasspath), addonInfo, onClasspath);
    }
    
    private LoadedAddonClassTransformer parseTransformer(final AddonClassLoader loader, final String name) {
        try {
            final Class<?> transformerClass = loader.loadClass(name);
            final Class<?> itf = loader.loadClass("net.labymod.api.addon.transform.AddonClassTransformer");
            if (!itf.isAssignableFrom(transformerClass)) {
                throw new AddonLoadException("Transformer " + name + " doesn't implement AddonTransformer");
            }
            final Object transformer = itf.cast(transformerClass.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]));
            return new WrappedAddonClassTransformer(itf, transformer);
        }
        catch (final ReflectiveOperationException exception) {
            throw new AddonInitException("Failed to instantiate addon transformer " + name);
        }
    }
    
    private List<String> loadMixinConfigs(final ClassLoader classLoader, final InstalledAddonInfo addonInfo, final boolean onClasspath) {
        final List<String> mixinConfigurations = new ArrayList<String>();
        final String runningVersion = this.labyModLoader.version().toString();
        if (onClasspath) {
            final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            final AtomicInteger index = new AtomicInteger();
            for (URL url : platformClassloader.getPotentialClasspathAddons()) {
                try {
                    mixinConfigurations.addAll(AddonResourceFinder.discoverResources(url, location -> {
                        final String name = location.name();
                        return name.startsWith(runningVersion) && name.endsWith(".mixins.json");
                    }, location -> {
                        final String name2 = location.name();
                        final String uniqueName = name2.replace((CharSequence)"-0", "-" + index.getAndIncrement());
                        try {
                            DefaultLabyModLoader.getInstance().registerMixinResourceSource(uniqueName, location.uri().toURL());
                        }
                        catch (final MalformedURLException exception2) {
                            return name2;
                        }
                        return uniqueName;
                    }));
                }
                catch (final Exception exception) {
                    exception.printStackTrace(System.err);
                }
            }
            return mixinConfigurations;
        }
        final String mixinConfiguration = String.format(Locale.ROOT, "%s-%s.mixins.json", runningVersion, addonInfo.getNamespace());
        final URL resource = classLoader.getResource(mixinConfiguration);
        if (resource != null) {
            mixinConfigurations.add(mixinConfiguration);
        }
        return mixinConfigurations;
    }
    
    private void prepareLoadedAddons() {
        Collection<LoadedAddon> loadedAddons = this.addonService.getLoadedAddons();
        final List<LoadedAddon> incompatibleAddons = new ArrayList<LoadedAddon>();
        for (final LoadedAddon loadedAddon : loadedAddons) {
            final String[] incompatibles = loadedAddon.info().getIncompatibleAddons();
            if (incompatibles == null) {
                continue;
            }
            for (final String incompatible : incompatibles) {
                final Optional<LoadedAddon> optionalAddon = this.addonService.getOptionalAddon(incompatible);
                if (!optionalAddon.isEmpty()) {
                    final LoadedAddon incompatibleAddon = optionalAddon.get();
                    this.logger.info("The addon \"{}\" is unloaded because an incompatible addon was found! ({})", loadedAddon.info().getNamespace(), incompatibleAddon.info().getNamespace());
                    incompatibleAddons.add(loadedAddon);
                    break;
                }
            }
        }
        if (!incompatibleAddons.isEmpty()) {
            this.addonService.unloadAddons(incompatibleAddons);
        }
        loadedAddons = this.addonService.getLoadedAddons();
        for (final LoadedAddon loadedAddon : loadedAddons) {
            this.logger.info("Initializing Addon {} v{}", loadedAddon.info().getNamespace(), loadedAddon.info().getVersion());
            this.processEntrypoints(loadedAddon);
            this.prepareEarlyAddonTransformation(loadedAddon);
        }
        this.invokeAddonEntryPoints(AddonEntryPoint.Point.EARLY_INITIALIZATION, null);
    }
    
    public void invokeAddonEntryPoints(final AddonEntryPoint.Point point, final LoadedAddon addon) {
        final List<WrappedAddonEntrypoint> sortedEntryPoints = this.addonEntrypoints.get(point);
        if (sortedEntryPoints == null) {
            return;
        }
        sortedEntryPoints.sort(Comparator.comparingInt(WrappedAddonEntrypoint::getPriority));
        for (final WrappedAddonEntrypoint sortedEntrypoint : sortedEntryPoints) {
            final LoadedAddon entrypointAddon = sortedEntrypoint.addon();
            if (addon != null && !addon.equals(entrypointAddon)) {
                continue;
            }
            try {
                EntrypointInvoker.invoke(sortedEntrypoint.entrypoint(), sortedEntrypoint.addon().getClassLoader());
            }
            catch (final AddonLoadException exception) {
                this.logger.error("The addon \"{}\" is unloaded because the \"{}\" entrypoint threw an exception!", entrypointAddon.info().getNamespace(), sortedEntrypoint.entrypoint().name(), (exception.getCause() == null) ? exception : exception.getCause());
                this.addonService.unloadAddon(entrypointAddon);
            }
        }
    }
    
    private void processEntrypoints(final LoadedAddon addon) {
        final InstalledAddonInfo info = addon.info();
        final Map<AddonEntryPoint.Point, List<AddonEntrypoint>> addonEntrypoints = info.getEntrypoints();
        if (addonEntrypoints == null || addonEntrypoints.isEmpty()) {
            return;
        }
        for (final Map.Entry<AddonEntryPoint.Point, List<AddonEntrypoint>> entry : addonEntrypoints.entrySet()) {
            final AddonEntryPoint.Point point = entry.getKey();
            final List<WrappedAddonEntrypoint> wrappedEntrypoints = this.addonEntrypoints.computeIfAbsent(point, k -> new ArrayList());
            for (final AddonEntrypoint addonEntrypoint : entry.getValue()) {
                if (addonEntrypoint.version() != null && !addonEntrypoint.version().equals(this.labyModLoader.version().toString())) {
                    continue;
                }
                wrappedEntrypoints.add(new WrappedAddonEntrypoint(addon, addonEntrypoint));
            }
        }
    }
    
    private void prepareEarlyAddonTransformation(final LoadedAddon addon) {
        final ClassLoader classLoader2 = addon.getClassLoader();
        if (classLoader2 instanceof final AddonClassLoader classLoader) {
            final InstalledAddonInfo addonInfo = addon.info();
            final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            platformClassloader.getAccessWidener().findAndCreateAccessWidener(classLoader, addonInfo.getNamespace(), ReflectLabyModLoader.invokeGetGameVersion());
            if (addonInfo.getEarlyTransformers() != null) {
                for (final String earlyTransformer : addonInfo.getEarlyTransformers()) {
                    platformClassloader.registerTransformer(PlatformClassloader.TransformerPhase.PRE, earlyTransformer);
                }
            }
            return;
        }
        throw new AddonLoadException("Unexpected AddonClassloader: " + addon.getClassLoader().getClass().getName());
    }
    
    public AddonClassLoader loadAddonClassLoader(final InstalledAddonInfo addonInfo, Path path) throws IOException {
        final String namespace = addonInfo.getNamespace();
        if (this.addonService.getAddon(addonInfo.getNamespace()).isPresent()) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Another addon was found that has the same namespace. (%s)", namespace));
        }
        if (!addonInfo.isCurrentOsSupported()) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Addon %s cannot run on %s", addonInfo.getDisplayName(), OperatingSystem.getPlatform()));
        }
        final String gameVersion = ReflectLabyModLoader.invokeGetGameVersion();
        if (!addonInfo.getCompatibleMinecraftVersions().isCompatible(VersionDeserializer.from(gameVersion))) {
            throw new UnsupportedAddonException(String.format(Locale.ROOT, "Addon %s cannot run on Minecraft %s", addonInfo.getDisplayName(), gameVersion));
        }
        final String mainClassName = addonInfo.getMainClass();
        if (mainClassName == null) {
            throw new AddonLoadException("No main class specified in the addon.json");
        }
        if (this.labyModLoader.isDevelopmentEnvironment()) {
            try {
                path = this.addonRemapper.remap(path);
            }
            catch (final Throwable throwable) {
                this.logger.error("Error occurred while remapping addon", throwable);
            }
        }
        final Collection<URL> classpath = new ArrayList<URL>(1);
        classpath.add(path.toUri().toURL());
        if (addonInfo.getMavenDependencies() != null) {
            classpath.addAll(Arrays.asList(this.installDependencies(addonInfo.getMavenDependencies())));
        }
        final AddonClassLoader addonClassLoader = new AddonClassLoader(classpath.toArray(new URL[0]), AddonPreparer.class.getClassLoader(), addonInfo);
        PlatformEnvironment.getPlatformClassloader().registerChildClassloader(namespace, addonClassLoader);
        return addonClassLoader;
    }
    
    private URL[] installDependencies(final MavenDependency[] dependencies) {
        final URL[] urls = new URL[dependencies.length];
        try {
            IOUtil.createDirectories(Constants.Files.LIBRARIES);
            for (int i = 0; i < dependencies.length; ++i) {
                final MavenDependency dependency = dependencies[i];
                final Path path = this.addonLoader.libraryLoader().loadMavenDependency(dependency);
                urls[i] = path.toUri().toURL();
            }
        }
        catch (final IOException exception) {
            throw new AddonLoadException("Failed to install maven dependencies for an addon", exception);
        }
        return urls;
    }
    
    public enum AddonPrepareContext
    {
        DEFAULT, 
        RUNTIME, 
        CLASSPATH;
    }
    
    record WrappedAddonEntrypoint(LoadedAddon addon, AddonEntrypoint entrypoint) {
        public int getPriority() {
            return this.entrypoint.priority();
        }
    }
}
