// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.ServiceConfigurationError;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ServiceLoader;
import java.io.IOException;
import net.labymod.core.util.classpath.ClasspathUtil;
import org.spongepowered.asm.mixin.Mixins;
import java.util.Locale;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.MixinEnvironment;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.labymod.api.volt.VoltMixinBootstrap;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.labymod.util.crashtracker.CrashTracker;
import java.util.Iterator;
import net.labymod.core.loader.version.VersionSearcher;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.labymod.core.loader.ReflectLabyModLoader;
import java.net.URL;
import net.minecraft.launchwrapper.classpath.Classpath;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.version.MojangVersionSearcher;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import net.labymod.core.loader.vanilla.launchwrapper.platform.LaunchWrapperPlatformClassloader;
import java.util.Objects;
import net.labymod.api.volt.asm.util.ASMContext;
import java.nio.file.Path;
import java.util.List;
import net.minecraft.launchwrapper.Launch;
import net.labymod.api.util.logging.Logging;
import net.minecraft.launchwrapper.ITweaker;

public class Java17LabyModLaunchWrapperTweaker implements ITweaker
{
    private static final Logging LOGGER;
    private static final String RUNNING_VERSION = "net.labymod.running-version";
    private static final String OBFUSCATED_JAR_PATH = "net.labymod.obfuscated-jar-path";
    private static final String MINECRAFT_MAIN_CLASS = "net.minecraft.client.main.Main";
    private static final String LABYMOD_MIXIN_SERVICE_CLASS = "net.labymod.core.loader.vanilla.launchwrapper.mixin.LabyModMixinService";
    private final LaunchWrapperTransformerRegistry transformerRegistry;
    private boolean labyModDevEnvironment;
    private boolean addonDevEnvironment;
    private String runningVersion;
    
    public Java17LabyModLaunchWrapperTweaker() {
        this.transformerRegistry = new LaunchWrapperTransformerRegistry(Launch.classLoader);
    }
    
    public void acceptOptions(final List<String> arguments, final Path gameDirectory, final Path assetsDirectory, final String profile, final boolean labyModDevEnvironment, final boolean addonDevelopmentEnvironment) {
        final LaunchClassLoader classLoader = Launch.classLoader;
        this.transformerRegistry.registerEarlyTransformers();
        this.setupExceptionHandler(classLoader);
        ASMContext.setDevEnvironment(labyModDevEnvironment);
        ASMContext.setPlatformClassLoader((ClassLoader)classLoader);
        final LaunchClassLoader obj = classLoader;
        Objects.requireNonNull(obj);
        ASMContext.setResourceFinder(obj::loadResource);
        try {
            this.registerPreExclusionRules(classLoader);
            this.labyModDevEnvironment = labyModDevEnvironment;
            this.transformerRegistry.setLabyModDevEnvironment(labyModDevEnvironment);
            final PlatformClassloader platformClassloader = new LaunchWrapperPlatformClassloader();
            this.transformerRegistry.setPlatformClassloader(platformClassloader);
            boolean developmentEnvironment = labyModDevEnvironment;
            if (addonDevelopmentEnvironment) {
                developmentEnvironment = true;
            }
            this.addonDevEnvironment = addonDevelopmentEnvironment;
            final String runningVersion = System.getProperty("net.labymod.running-version", null);
            final Path obfuscatedJarPath = (System.getProperty("net.labymod.obfuscated-jar-path") == null) ? null : Paths.get(System.getProperty("net.labymod.obfuscated-jar-path"), new String[0]);
            if (runningVersion == null && developmentEnvironment) {
                throw new IllegalStateException("Development environment was not set up correctly. (VM argument \"net.labymod.running-version\" is missing)");
            }
            if ((obfuscatedJarPath == null || !Files.exists(obfuscatedJarPath, new LinkOption[0])) && developmentEnvironment) {
                Java17LabyModLaunchWrapperTweaker.LOGGER.warn("Development environment was not set up correctly. (VM argument \"net.labymod.obfuscated-jar-path\" is missing or file does not exist)", new Object[0]);
            }
            final VersionSearcher searcher = new MojangVersionSearcher(runningVersion);
            searcher.searchClasspath(platformClassloader);
            this.runningVersion = searcher.getMinecraftVersion();
            this.transformerRegistry.setRunningVersion(this.runningVersion);
            PlatformEnvironment.setClientJarPath(searcher.getClientJarPath());
            PlatformEnvironment.setObfuscatedJarPath((obfuscatedJarPath == null && !developmentEnvironment) ? searcher.getClientJarPath() : obfuscatedJarPath);
            if (!addonDevelopmentEnvironment) {
                for (final URL url : Classpath.potentialClasspathAddons()) {
                    classLoader.addURL(url);
                }
            }
            ReflectLabyModLoader.invokeGameOptions(arguments, gameDirectory, assetsDirectory, profile, this.labyModDevEnvironment, addonDevelopmentEnvironment, this.runningVersion);
        }
        catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader classLoader) {
        this.loadRenderdoc();
        this.registerPostExclusionRules(classLoader);
        final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
        this.transformerRegistry.registerPreTransformers();
        this.configureMixin();
        this.transformerRegistry.registerPostTransformers();
        this.initializeMixins(platformClassloader);
    }
    
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
    
    public String[] getLaunchArguments() {
        return ReflectLabyModLoader.invokeLaunchArguments();
    }
    
    private void setupExceptionHandler(final LaunchClassLoader classLoader) {
        try {
            classLoader.addClassLoaderExclusion("net.labymod.util.crashtracker.");
            Launch.exceptionHandler = CrashTracker::findCause;
        }
        catch (final Exception exception) {
            Java17LabyModLaunchWrapperTweaker.LOGGER.error("Could not set the exception handler!", exception);
        }
    }
    
    private void registerPreExclusionRules(final LaunchClassLoader classLoader) {
        classLoader.addClassLoaderExclusion("net.labymod.api.loader.platform.");
        classLoader.addClassLoaderExclusion("net.labymod.core.loader.vanilla.");
        classLoader.addClassLoaderExclusion("net.labymod.core.loader.isolated.util.");
        classLoader.addClassLoaderExclusion("org.apache.logging.log4j.");
    }
    
    private void registerPostExclusionRules(final LaunchClassLoader classLoader) {
        classLoader.addTransformerExclusion("net.labymod.autogen.api.");
        classLoader.addTransformerExclusion("net.labymod.autogen.core.");
        classLoader.addTransformerExclusion("net.labymod.api.");
        classLoader.addTransformerExclusion("net.labymod.core.");
        classLoader.addClassLoaderExclusion("org.spongepowered.asm.service.");
        classLoader.addClassLoaderExclusion("net.labymod.core.loader.vanilla.launchwrapper.mixin.");
        classLoader.addClassLoaderExclusion("com.llamalad7.mixinextras.sugar.ref.");
        classLoader.addClassLoaderExclusion("com.llamalad7.mixinextras.sugar.impl.ref.generated.");
    }
    
    private void configureMixin() {
        this.forceMixinService();
        ReflectLabyModLoader.invokeLoadIsolatedLibraries();
        System.setProperty("mixin.env.remapRefMap", "true");
        MixinBootstrap.init();
        VoltMixinBootstrap.initialize();
        MixinExtrasBootstrap.init();
        final MixinEnvironment defaultEnvironment = MixinEnvironment.getDefaultEnvironment();
        defaultEnvironment.setSide(MixinEnvironment.Side.CLIENT);
        defaultEnvironment.setObfuscationContext("searge");
    }
    
    private void initializeMixins(final PlatformClassloader platformClassloader) {
        try {
            final ClassLoader loader = platformClassloader.getPlatformClassloader();
            if (this.labyModDevEnvironment || this.addonDevEnvironment) {
                final String mixinConfigFormat = "%s-labymod4-%d.mixins.json";
                int index = 0;
                final List<String> configurations = new ArrayList<String>();
                while (true) {
                    final boolean zeroIndex = index == 0;
                    final String format = String.format(Locale.ROOT, mixinConfigFormat, this.runningVersion, index++);
                    if (loader.getResource(format) == null) {
                        if (!zeroIndex) {
                            break;
                        }
                        continue;
                    }
                    else {
                        configurations.add(format);
                    }
                }
                Mixins.addConfigurations((String[])configurations.toArray(new String[0]));
            }
            else {
                List<String> mixinConfigurations = ClasspathUtil.rootResources("labymod", ClasspathUtil.class);
                mixinConfigurations = mixinConfigurations.stream().filter(configuration -> configuration.startsWith(this.runningVersion) && configuration.endsWith(".mixins.json")).toList();
                Mixins.addConfigurations((String[])mixinConfigurations.toArray(new String[0]));
            }
            ReflectLabyModLoader.invokeLoadAddons();
        }
        catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    private void forceMixinService() {
        try {
            final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
            final Class<?> iMixinServiceCls = platformClassloader.findClass("org.spongepowered.asm.service.IMixinService");
            final Class<?> cls = platformClassloader.findClass("org.spongepowered.asm.service.MixinService");
            final Method getInstanceMethod = cls.getDeclaredMethod("getInstance", (Class<?>[])new Class[0]);
            getInstanceMethod.setAccessible(true);
            final Object mixinServiceInstance = getInstanceMethod.invoke(null, new Object[0]);
            final Field serviceLoaderField = mixinServiceInstance.getClass().getDeclaredField("serviceLoader");
            serviceLoaderField.setAccessible(true);
            final ServiceLoader<?> serviceLoader = ServiceLoader.load(iMixinServiceCls, Java17LabyModLaunchWrapperTweaker.class.getClassLoader());
            final Object targetService = this.getTargetService(serviceLoader);
            final Field serviceField = cls.getDeclaredField("service");
            serviceField.setAccessible(true);
            serviceField.set(mixinServiceInstance, targetService);
        }
        catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    private Object getTargetService(final ServiceLoader<?> loader) {
        try {
            for (final Object service : loader) {
                if (service.getClass().getName().contains("net.labymod.core.loader.vanilla.launchwrapper.mixin.LabyModMixinService")) {
                    return service;
                }
            }
        }
        catch (final ServiceConfigurationError error) {
            return this.getTargetService(loader);
        }
        return null;
    }
    
    private void loadRenderdoc() {
        final LaunchClassLoader classLoader = Launch.classLoader;
        try {
            final Class<?> renderDocClass = classLoader.findClass("net.labymod.api.util.debug.Renderdoc");
            renderDocClass.getMethod("load", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        }
        catch (final ReflectiveOperationException ignored) {
            ignored.printStackTrace(System.err);
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("LabyMod Tweaker");
    }
}
