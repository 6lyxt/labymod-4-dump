// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon;

import net.labymod.api.addon.exception.AddonInvalidException;
import java.util.Locale;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.event.addon.lifecycle.AddonPostEnableEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.addon.lifecycle.AddonEnableEvent;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import java.util.Iterator;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.configuration.converter.LegacyConverter;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.settings.type.AbstractSetting;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.client.component.Component;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.Laby;
import java.util.HashMap;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.loader.ConfigAccessor;
import java.util.Map;

public abstract class LabyAddon<T extends AddonConfig>
{
    private final LabyAddonConfigProvider<? extends T> configProvider;
    private final Map<Class<? extends ConfigAccessor>, LabyAddonConfigProvider<?>> customConfigProviders;
    private final LabyAPI labyAPI;
    private final Logging logging;
    private ReferenceStorageAccessor referenceStorageAccessor;
    private LabyAddon<T> instance;
    private InstalledAddonInfo addonInfo;
    private boolean registeredCategory;
    private boolean loadedInRuntime;
    
    protected LabyAddon() {
        this.logging = Logging.create(this.getClass());
        this.customConfigProviders = new HashMap<Class<? extends ConfigAccessor>, LabyAddonConfigProvider<?>>();
        this.labyAPI = Laby.labyAPI();
        if (this.configurationClass() == null) {
            this.addonExceptionMessage("The addons configuration class is null!", new Object[0]);
        }
        this.preConfigurationLoad();
        (this.configProvider = (LabyAddonConfigProvider<? extends T>)new LabyAddonConfigProvider<T>(this.configurationClass())).safeLoad(JsonConfigLoader.createDefault());
        if (this.configuration().enabled() == null) {
            this.addonExceptionMessage("The addon has to implement the enabled setting inherited by AddonConfig", new Object[0]);
        }
    }
    
    public final T configuration() {
        return (T)this.configProvider.get();
    }
    
    public final LabyAPI labyAPI() {
        return this.labyAPI;
    }
    
    public final Logging logger() {
        return this.logging;
    }
    
    public final InstalledAddonInfo addonInfo() {
        return this.addonInfo;
    }
    
    public final boolean wasLoadedInRuntime() {
        return this.loadedInRuntime;
    }
    
    public final void sendMessage(final String message) {
        this.labyAPI.minecraft().chatExecutor().chat(message);
    }
    
    public final void displayMessage(final String message) {
        this.displayMessage(Component.text(message));
    }
    
    public final void displayMessage(final Component message) {
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(message);
    }
    
    protected void preConfigurationLoad() {
    }
    
    protected void load() {
    }
    
    protected abstract void enable();
    
    protected abstract Class<? extends T> configurationClass();
    
    protected final void registerSettingCategory() {
        if (this.registeredCategory) {
            this.addonExceptionMessage("Cannot register the same category twice", new Object[0]);
        }
        final T configuration = this.configuration();
        if (configuration == null) {
            this.addonExceptionMessage("Cannot register the category because configuration is null", new Object[0]);
        }
        final RootSettingRegistry registry = RootSettingRegistry.addon(this, configuration);
        this.labyAPI.coreSettingRegistry().addSetting(registry);
        this.registeredCategory = true;
    }
    
    protected final void registerListener(@NotNull final Object listener) {
        Objects.requireNonNull(listener, "Listener");
        this.labyAPI.eventBus().registerListener(listener);
    }
    
    protected final void registerLegacyConverter(@NotNull final LegacyConverter<?> legacyConverter) {
        Objects.requireNonNull(legacyConverter, "legacyConverter");
        Laby.references().legacyConfigConverter().register(legacyConverter);
    }
    
    protected final void registerCommand(@NotNull final Command command) {
        Objects.requireNonNull(command, "Command");
        this.labyAPI.commandService().register(command);
    }
    
    public final void saveConfiguration() throws ConfigurationSaveException {
        this.configProvider.safeSave();
    }
    
    protected final <C extends ConfigAccessor> C addCustomConfiguration(@NotNull final Class<C> configurationClass) throws ConfigurationLoadException {
        Objects.requireNonNull(configurationClass, "Custom Configuration Class");
        final Class<? extends T> mainConfigurationClass = this.configurationClass();
        if (mainConfigurationClass == configurationClass) {
            this.addonExceptionMessage("Cannot add the main addon configuration as custom configuration", new Object[0]);
        }
        if (this.customConfigProviders.containsKey(configurationClass)) {
            this.addonExceptionMessage("The custom configuration %s was already loaded", configurationClass.getName());
        }
        final String mainConfigurationName = ConfigLoader.getName(mainConfigurationClass);
        if (mainConfigurationName.equals(ConfigLoader.getName(configurationClass))) {
            this.addonExceptionMessage("Configuration %s has the same ConfigName as the main configuration", configurationClass.getName());
        }
        for (final Class<? extends ConfigAccessor> value : this.customConfigProviders.keySet()) {
            if (mainConfigurationName.equals(ConfigLoader.getName(value))) {
                this.addonExceptionMessage("Configuration %s has the same ConfigName as the custom configuration %s", configurationClass.getName(), value);
                break;
            }
        }
        final LabyAddonConfigProvider<C> customConfigProvider = new LabyAddonConfigProvider<C>(configurationClass);
        final C config = customConfigProvider.safeLoad(JsonConfigLoader.createDefault());
        this.customConfigProviders.put(configurationClass, customConfigProvider);
        return config;
    }
    
    protected final <C extends ConfigAccessor> void saveCustomConfiguration(@NotNull final Class<C> configurationClass) throws ConfigurationSaveException {
        Objects.requireNonNull(configurationClass, "Custom Configuration Class");
        final ConfigProvider<?> customConfigProvider = this.customConfigProviders.get(configurationClass);
        if (customConfigProvider == null) {
            this.addonExceptionMessage("Cannot save the custom configuration %s as it isn't declared as an custom configuration", configurationClass.getName());
        }
        customConfigProvider.safeSave();
    }
    
    @Subscribe
    public final void onAddonLoad(final AddonEnableEvent event) {
        this.loadedInRuntime = this.labyAPI.isFullyInitialized();
        this.addonInfo = event.addon().info();
        this.instance = (LabyAddon)event.getInstance();
        this.referenceStorageAccessor = event.getReferenceStorageAccessor();
        if (this.configuration() != null) {
            this.labyAPI.addonService().registerMainConfiguration(this.addonInfo.getNamespace(), this.configuration());
        }
        try {
            this.load();
        }
        catch (final Exception e) {
            this.addonException("Failed to load the addon", e);
        }
    }
    
    @Subscribe
    public final void onAddonInitialize(final AddonPostEnableEvent event) {
        try {
            this.enable();
        }
        catch (final Exception e) {
            this.addonException("Failed to enable the addon", e);
        }
    }
    
    @Subscribe
    public final void onAddonSettingsSave(final ConfigurationSaveEvent event) {
        this.saveConfiguration();
    }
    
    private void addonExceptionMessage(String message, final Object... arguments) {
        message = String.format(Locale.ROOT, message, arguments);
        this.addonException(message, new AddonInvalidException(message));
    }
    
    private void addonException(final String message, final Exception exception) throws RuntimeException {
        if (this.labyAPI.labyModLoader().isAddonDevelopmentEnvironment() && (this.addonInfo == null || this.addonInfo.getNamespace().equals(this.labyAPI.addonService().getClassPathAddon()))) {
            this.labyAPI.minecraft().crashGame(message, exception);
            return;
        }
        throw (exception instanceof RuntimeException) ? exception : new RuntimeException(exception);
    }
    
    @Deprecated
    protected final <A extends LabyAddon<T>> A getAddonInstance() {
        if (this.instance == null) {
            return null;
        }
        return (A)this.instance;
    }
    
    @NotNull
    protected final <A extends LabyAddon<T>> A addonInstance() {
        if (this.instance == null) {
            throw new NullPointerException("Addon instance is not initialized yet!");
        }
        return (A)this.instance;
    }
    
    @Deprecated
    protected final <R extends ReferenceStorageAccessor> R getReferenceStorageAccessor() {
        if (this.referenceStorageAccessor == null) {
            return null;
        }
        return (R)this.referenceStorageAccessor;
    }
    
    @NotNull
    protected final <R extends ReferenceStorageAccessor> R referenceStorageAccessor() {
        if (this.referenceStorageAccessor == null) {
            throw new NullPointerException("Reference storage is not initialized yet!");
        }
        return (R)this.referenceStorageAccessor;
    }
    
    public class LabyAddonConfigProvider<C extends ConfigAccessor> extends ConfigProvider<C>
    {
        private final Class<C> configurationClass;
        
        private LabyAddonConfigProvider(final LabyAddon this$0, final Class<C> configurationClass) {
            this.configurationClass = configurationClass;
        }
        
        @Override
        protected Class<C> getType() {
            return this.configurationClass;
        }
    }
}
