// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import java.lang.reflect.Method;
import java.util.stream.Stream;
import net.labymod.api.property.Property;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.event.addon.lifecycle.AddonStateChangeEvent;
import org.jetbrains.annotations.Nullable;
import java.util.function.Function;
import java.util.Locale;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.event.addon.lifecycle.GlobalAddonPostEnableEvent;
import net.labymod.api.event.addon.lifecycle.AddonPostEnableEvent;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import java.lang.reflect.Constructor;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.models.addon.annotation.AddonEntryPoint;
import net.labymod.api.event.addon.lifecycle.GlobalAddonEnableEvent;
import net.labymod.api.event.addon.lifecycle.AddonEnableEvent;
import net.labymod.api.addon.exception.AddonInitException;
import net.labymod.api.util.reflection.Reflection;
import java.lang.reflect.InvocationTargetException;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.reference.ReferenceStorageFinder;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.event.EventBus;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.mixin.Mixins;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.addon.info.AddonMeta;
import java.util.ArrayList;
import java.util.Collection;
import net.labymod.core.addon.loader.AddonValidator;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.addon.LoadedAddon;
import java.util.Map;
import net.labymod.api.addon.AddonService;
import net.labymod.api.service.Service;

public class DefaultAddonService extends Service implements AddonService
{
    private static final StackWalker WALKER;
    private static final DefaultAddonService instance;
    private final AddonLoader addonLoader;
    private final ServerAPIAddonService serverAPIAddonService;
    private final Map<String, LoadedAddon> loadedAddons;
    private final Map<String, AddonConfig> addonConfigs;
    private boolean enabled;
    private String classPathAddonNamespace;
    
    public static DefaultAddonService getInstance() {
        return DefaultAddonService.instance;
    }
    
    private DefaultAddonService() {
        this.addonLoader = new AddonLoader(this);
        this.serverAPIAddonService = new ServerAPIAddonService(this);
        this.loadedAddons = new ConcurrentHashMap<String, LoadedAddon>();
        this.addonConfigs = new HashMap<String, AddonConfig>();
    }
    
    public ServerAPIAddonService serverAPI() {
        return this.serverAPIAddonService;
    }
    
    @Override
    public InstalledAddonInfo getAddonInfo(final String namespace) {
        if (this.loadedAddons.containsKey(namespace)) {
            return this.loadedAddons.get(namespace).info();
        }
        return null;
    }
    
    @NotNull
    @Override
    public Optional<LoadedAddon> getAddon(final String namespace) {
        return Optional.ofNullable(this.loadedAddons.get(namespace));
    }
    
    @Override
    public Optional<LoadedAddon> getOptionalAddon(final String namespace) {
        if (!AddonValidator.isMatchingNamespaceFormat(namespace)) {
            return Optional.empty();
        }
        return this.loadedAddons.containsKey(namespace) ? Optional.of(this.loadedAddons.get(namespace)) : Optional.empty();
    }
    
    @NotNull
    @Override
    public Optional<LoadedAddon> getAddon(final ClassLoader classLoader) {
        if (!(classLoader instanceof AddonClassLoader)) {
            return Optional.empty();
        }
        return this.getAddon(((AddonClassLoader)classLoader).getAddonInfo().getNamespace());
    }
    
    @Override
    public Collection<LoadedAddon> getLoadedAddons() {
        return this.loadedAddons.values();
    }
    
    @Override
    public Collection<LoadedAddon> getVisibleAddons() {
        final List<LoadedAddon> list = new ArrayList<LoadedAddon>();
        for (final LoadedAddon addon : this.getLoadedAddons()) {
            if (!addon.info().hasMeta(AddonMeta.HIDDEN)) {
                list.add(addon);
            }
        }
        return list;
    }
    
    public void unloadAddon(@NotNull final LoadedAddon loadedAddon) {
        this.loadedAddons.remove(loadedAddon.info().getNamespace());
    }
    
    public void unloadAddons(@NotNull final Collection<LoadedAddon> collection) {
        for (final LoadedAddon loadedAddon : collection) {
            this.unloadAddon(loadedAddon);
        }
    }
    
    public void addLoadedAddon(final LoadedAddon addon, final boolean runtime) {
        this.loadedAddons.put(addon.info().getNamespace(), addon);
        if (!addon.getMixinConfigs().isEmpty()) {
            Mixins.addConfigurations((String[])addon.getMixinConfigs().toArray(new String[0]));
            for (final Config config : Mixins.getConfigs()) {
                for (final String mixinConfig : addon.getMixinConfigs()) {
                    if (!config.getName().equals(mixinConfig)) {
                        continue;
                    }
                    if (config.getConfig().hasDecoration("labymod-namespace")) {
                        continue;
                    }
                    config.getConfig().decorate("labymod-namespace", (Object)addon.info().getNamespace());
                }
            }
        }
        if (this.enabled) {
            this.enableAddon(addon, runtime);
        }
    }
    
    public void registerAddonResourcePacks() {
        for (final LoadedAddon addon : this.loadedAddons.values()) {
            LabyMod.getInstance().renderPipeline().resourcePackRepository().registerLoadedAddonPack(addon);
        }
    }
    
    public void enableAddons() {
        this.enabled = true;
        for (final LoadedAddon addon : this.loadedAddons.values()) {
            try {
                this.enableAddon(addon);
            }
            catch (final Exception e) {
                this.addonEnableThrowable(addon, e);
            }
        }
    }
    
    public void postEnableAddons() {
        final EventBus eventBus = Laby.references().eventBus();
        for (final LoadedAddon addon : this.loadedAddons.values()) {
            this.postEnableAddon(eventBus, addon);
        }
    }
    
    private void enableAddon(final LoadedAddon addon) {
        this.enableAddon(addon, false);
    }
    
    private void enableAddon(final LoadedAddon addon, final boolean runtime) {
        if (runtime) {
            LabyMod.getInstance().renderPipeline().resourcePackRepository().registerLoadedAddonSilentPacket(addon);
        }
        final Class<?> mainClass = addon.getMainClass();
        final AtomicReference<Object> instanceReference = new AtomicReference<Object>();
        ReferenceStorageAccessor referenceStorageAccessor = null;
        try {
            final ReferenceStorageAccessor accessor = referenceStorageAccessor = ReferenceStorageFinder.find(addon.getClassLoader());
            Reflection.getMethods(accessor.getClass(), false, method -> {
                if (instanceReference.get() != null) {
                    return;
                }
                else {
                    final Class<?> returnType = method.getReturnType();
                    if (returnType == Object.class || returnType == LabyAddon.class || !returnType.isAssignableFrom(mainClass)) {
                        return;
                    }
                    else {
                        try {
                            instanceReference.set(method.invoke(accessor, new Object[0]));
                        }
                        catch (final IllegalAccessException | InvocationTargetException e2) {
                            throw new RuntimeException(e2);
                        }
                        return;
                    }
                }
            });
        }
        catch (final Exception e) {
            final String namespace = addon.info().getNamespace();
            if (e instanceof IllegalStateException) {
                DefaultAddonService.LOGGER.debug("Cannot load reference storage of " + namespace, (Throwable)e);
            }
            else if (e instanceof final RuntimeException runtimeException) {
                final Throwable cause = runtimeException.getCause();
                if (cause instanceof IllegalAccessException) {
                    DefaultAddonService.LOGGER.error("Cannot access reference storage " + namespace, cause);
                }
                else {
                    if (cause instanceof InvocationTargetException) {
                        this.addonEnableThrowable(addon, new RuntimeException("Cannot invoke reference storage " + namespace, cause));
                        return;
                    }
                    DefaultAddonService.LOGGER.debug("Cannot load reference storage " + namespace, (Throwable)e);
                }
            }
            else {
                DefaultAddonService.LOGGER.debug("Cannot load reference storage " + namespace, (Throwable)e);
            }
        }
        if (instanceReference.get() == null) {
            try {
                final Constructor<?> constructor = Reflection.searchEmptyConstructor(mainClass);
                if (constructor != null) {
                    instanceReference.set(constructor.newInstance(new Object[0]));
                }
            }
            catch (final Exception e) {
                this.addonEnableThrowable(addon, e);
                return;
            }
        }
        final Object instance = instanceReference.get();
        if (instance == null) {
            this.addonEnableThrowable(addon, new AddonInitException("Failed to create main instance"));
            return;
        }
        final LabyMod labyMod = LabyMod.getInstance();
        final EventBus eventBus = labyMod.eventBus();
        eventBus.registerListener(instance);
        eventBus.fire(addon.getClassLoader(), new AddonEnableEvent(addon, instance, referenceStorageAccessor));
        eventBus.fire(new GlobalAddonEnableEvent(addon, instance));
        this.addonLoader.addonPreparer().invokeAddonEntryPoints(AddonEntryPoint.Point.LOAD, addon);
        if (labyMod.isFullyInitialized()) {
            this.postEnableAddon(eventBus, addon);
        }
    }
    
    private void postEnableAddon(final EventBus eventBus, final LoadedAddon addon) {
        try {
            eventBus.fire(new ServiceLoadEvent(addon.getClassLoader(), ServiceLoadEvent.State.ADDON_LOADED));
            eventBus.fire(addon.getClassLoader(), new AddonPostEnableEvent(addon));
            eventBus.fire(new GlobalAddonPostEnableEvent(addon.info()));
            this.addonLoader.addonPreparer().invokeAddonEntryPoints(AddonEntryPoint.Point.ENABLE, addon);
        }
        catch (final Throwable throwable) {
            this.addonEnableThrowable(addon, throwable);
        }
    }
    
    private void addonEnableThrowable(final LoadedAddon loadedAddon, final Throwable throwable) {
        if (DefaultLabyModLoader.getInstance().isAddonDevelopmentEnvironment() && loadedAddon.info().getNamespace().equals(this.classPathAddonNamespace)) {
            LabyMod.getInstance().minecraft().crashGame("Unable to enable addon " + loadedAddon.info().getNamespace(), throwable);
            return;
        }
        DefaultAddonService.LOGGER.error("Unable to enable addon {}.", loadedAddon.info().getNamespace(), throwable);
    }
    
    @Override
    public Class<?> loadClassFromAddon(final String name) throws ClassNotFoundException {
        for (final LoadedAddon addon : this.loadedAddons.values()) {
            if (addon.getClassLoader() instanceof AddonClassLoader && !((AddonClassLoader)addon.getClassLoader()).containsClass(name)) {
                continue;
            }
            try {
                return addon.getClassLoader().loadClass(name);
            }
            catch (final ClassNotFoundException ex) {
                continue;
            }
            break;
        }
        throw new ClassNotFoundException(String.format(Locale.ROOT, "Class %s not found on addon classpath, searched %d addons", name, this.loadedAddons.size()));
    }
    
    @Override
    public LoadedAddon getLastCallerAddon() {
        final ResultWrapper result = DefaultAddonService.WALKER.walk(stream -> stream.map(frame -> {
            try {
                final Class<?> addonClass = this.loadClassFromAddon(frame.getClassName());
                final Optional<LoadedAddon> addon = this.getAddon(addonClass);
                return (ResultWrapper)addon.map((Function<? super LoadedAddon, ?>)ResultWrapper::new).orElse(ResultWrapper.EMPTY);
            }
            catch (final ClassNotFoundException ex) {
                return ResultWrapper.EMPTY;
            }
        }).filter(wrapper -> wrapper.addon() != null).findAny().orElse(ResultWrapper.EMPTY));
        return result.addon();
    }
    
    @Nullable
    @Override
    public String getClassPathAddon() {
        return this.classPathAddonNamespace;
    }
    
    @Override
    protected void prepare() {
        Laby.references().eventBus().registerListener(this.serverAPIAddonService);
        this.addonLoader.prepareSynchronously();
    }
    
    @Override
    public void registerMainConfiguration(final String namespace, final AddonConfig addonConfig) {
        if (this.hasMainConfiguration(namespace)) {
            throw new UnsupportedOperationException("Addon " + namespace + " already has a main configuration");
        }
        final ConfigProperty<Boolean> enabled = addonConfig.enabled();
        if (enabled != null) {
            Laby.fireEvent(new AddonStateChangeEvent(namespace, null, enabled.get()));
            enabled.addChangeListener((type, oldValue, newValue) -> Laby.fireEvent(new AddonStateChangeEvent(namespace, oldValue, newValue)));
        }
        this.addonConfigs.put(namespace, addonConfig);
    }
    
    @Override
    public boolean isEnabled(final Object instance) {
        return this.isEnabled(Laby.labyAPI().getNamespace(instance));
    }
    
    @Override
    public boolean isEnabled(final Class<?> cls) {
        return this.isEnabled(Laby.labyAPI().getNamespace(cls));
    }
    
    @Override
    public boolean isEnabled(final String namespace) {
        if (namespace.equals("labymod")) {
            return true;
        }
        final Optional<LoadedAddon> addon = this.getAddon(namespace);
        if (addon.isEmpty()) {
            return false;
        }
        final AddonConfig config = this.getMainConfiguration(namespace);
        return config == null || (config.enabled() != null && config.enabled().get());
    }
    
    @Override
    public boolean isEnabled(final InstalledAddonInfo addonInfo, final boolean backgroundMeta) {
        return addonInfo != null && ((backgroundMeta && addonInfo.hasMeta(AddonMeta.BACKGROUND)) || this.isEnabled(addonInfo.getNamespace()));
    }
    
    @Override
    public boolean isForceDisabled(final String namespace) {
        return this.serverAPIAddonService.isForceDisabled(namespace);
    }
    
    @Override
    public boolean hasMainConfiguration(final String namespace) {
        return this.addonConfigs.containsKey(namespace);
    }
    
    public AddonConfig getMainConfiguration(final String namespace) {
        return this.addonConfigs.get(namespace);
    }
    
    public AddonLoader addonLoader() {
        return this.addonLoader;
    }
    
    public boolean shouldApplyDynamicMixin(final String key) {
        if (key.equals("optifine")) {
            return OptiFine.isPresent();
        }
        return this.getAddon(key).isPresent();
    }
    
    public void setClassPathAddonNamespace(final String classPathAddonNamespace) {
        this.classPathAddonNamespace = classPathAddonNamespace;
    }
    
    static {
        WALKER = StackWalker.getInstance();
        instance = new DefaultAddonService();
    }
    
    record ResultWrapper(@Nullable LoadedAddon addon) {
        public static final ResultWrapper EMPTY;
        
        @Nullable
        public LoadedAddon addon() {
            return this.addon;
        }
        
        static {
            EMPTY = new ResultWrapper(null);
        }
    }
}
