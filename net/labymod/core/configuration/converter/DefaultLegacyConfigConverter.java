// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter;

import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import com.google.gson.JsonElement;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import net.labymod.core.configuration.converter.addon.OptifineLegacyAddonResolver;
import net.labymod.core.configuration.converter.addon.CustomNameTagsAddonResolver;
import net.labymod.core.configuration.converter.addon.DirectoryLegacyAddonResolver;
import net.labymod.core.configuration.converter.converter.LegacyLabyModuleConverter;
import net.labymod.core.configuration.converter.converter.LegacyChatFilterConverter;
import net.labymod.core.configuration.converter.converter.LegacyPlayerMenuConverter;
import net.labymod.core.configuration.converter.converter.LegacyAccountConverter;
import net.labymod.core.configuration.converter.converter.LegacyAutoTextConverter;
import net.labymod.core.configuration.converter.converter.LegacySettingConverter;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.Laby;
import java.util.Set;
import net.labymod.api.util.ThreadSafe;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.io.IOUtil;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import java.util.Map;
import net.labymod.api.configuration.converter.LegacyConverter;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.configuration.converter.LegacyConfigConverter;

@Singleton
@Implements(LegacyConfigConverter.class)
public class DefaultLegacyConfigConverter implements LegacyConfigConverter
{
    private static final Logging LOGGER;
    private final List<LegacyConverter<?>> legacyConverters;
    private final boolean legacyDirectoryExists;
    private final Map<String, String> legacyToModernAddons;
    private final Map<String, LegacyAddonConverter.Version> addonNamespaceVersions;
    private final Collection<LegacyAddonResolver> addonResolvers;
    
    public DefaultLegacyConfigConverter() {
        this.legacyToModernAddons = new HashMap<String, String>();
        this.addonNamespaceVersions = new HashMap<String, LegacyAddonConverter.Version>();
        this.addonResolvers = new ArrayList<LegacyAddonResolver>();
        this.legacyConverters = new ArrayList<LegacyConverter<?>>();
        this.legacyDirectoryExists = IOUtil.exists(LegacyConverter.LEGACY_PATH);
        this.registerDefaults();
    }
    
    @Override
    public void register(@NotNull final LegacyConverter<?> legacyConverter) {
        Objects.requireNonNull(legacyConverter, "legacyConverter");
        this.legacyConverters.add(legacyConverter);
        if (!(legacyConverter instanceof LegacyAddonConverter)) {
            legacyConverter.load();
        }
    }
    
    @Override
    public void registerAddon(@NotNull final String namespace, @NotNull final String... legacyUuids) {
        for (final String legacyUuid : legacyUuids) {
            this.legacyToModernAddons.put(legacyUuid, namespace);
        }
    }
    
    @Override
    public void registerAddonResolver(@NotNull final LegacyAddonResolver resolver) {
        this.addonResolvers.add(resolver);
    }
    
    @Nullable
    @Override
    public String getModernAddonNamespace(@NotNull final String legacyUuid) {
        return this.legacyToModernAddons.get(legacyUuid);
    }
    
    @NotNull
    @Override
    public Collection<LegacyConverter<?>> getConverters() {
        return this.legacyConverters;
    }
    
    @NotNull
    @Override
    public Collection<LegacyConverter<?>> getConverters(@NotNull final String namespace) {
        return this.legacyConverters.stream().filter(converter -> converter.getNamespace().equals(namespace)).collect((Collector<? super Object, ?, Collection<LegacyConverter<?>>>)Collectors.toList());
    }
    
    @Override
    public boolean hasLegacyInstallation() {
        return this.legacyDirectoryExists;
    }
    
    @Override
    public boolean hasStuffToConvert(@NotNull final String namespace) {
        if (!this.hasLegacyInstallation()) {
            return false;
        }
        for (final LegacyConverter<?> converter : this.getConverters(namespace)) {
            this.prepareConverter(converter);
            try {
                if (!converter.isEmpty() && converter.hasStuffToConvert()) {
                    return true;
                }
                continue;
            }
            catch (final Exception e) {
                DefaultLegacyConfigConverter.LOGGER.error("An error occurred while testing whether {} has stuff to convert", converter.getClass().getSimpleName(), e);
            }
        }
        return false;
    }
    
    @Override
    public boolean wasConversionAsked(@NotNull final String namespace) {
        ThreadSafe.ensureRenderThread();
        return Laby.labyAPI().config().other().conversionAskedNamespaces().get().contains(namespace);
    }
    
    @Override
    public void setConversionAsked(@NotNull final String namespace) {
        ThreadSafe.ensureRenderThread();
        Laby.labyAPI().config().other().conversionAskedNamespaces().get().add(namespace);
        Laby.fireEvent(new ConfigurationSaveEvent());
    }
    
    @Override
    public void useVersion(@NotNull final String addonNamespace, @NotNull final LegacyAddonConverter.Version version) {
        this.addonNamespaceVersions.put(addonNamespace, version);
    }
    
    @Override
    public int convert(@NotNull final String namespace) {
        final Collection<LegacyConverter<?>> converters = this.getConverters(namespace);
        int executed = 0;
        for (final LegacyConverter<?> converter : converters) {
            this.prepareConverter(converter);
            if (converter.isEmpty()) {
                continue;
            }
            try {
                converter.convert();
                ++executed;
            }
            catch (final Exception e) {
                DefaultLegacyConfigConverter.LOGGER.error("An error occurred while converting with {}", converter.getClass().getSimpleName(), e);
            }
        }
        return executed;
    }
    
    private void prepareConverter(final LegacyConverter<?> converter) {
        if (!(converter instanceof LegacyAddonConverter) || converter.wasLoaded()) {
            return;
        }
        final LegacyAddonConverter<?> addonConverter = (LegacyAddonConverter)converter;
        final LegacyAddonConverter.Version version = this.addonNamespaceVersions.get(converter.getNamespace());
        if (version != null) {
            addonConverter.load(version);
            return;
        }
        for (final LegacyAddonConverter.Version v : LegacyAddonConverter.Version.values()) {
            addonConverter.load(v);
            if (addonConverter.getValue() != null) {
                break;
            }
        }
    }
    
    private void registerDefaults() {
        final LegacySettingConverter settingConverter = new LegacySettingConverter();
        this.register(settingConverter);
        this.register(new LegacyAutoTextConverter());
        this.register(new LegacyAccountConverter());
        this.register(new LegacyPlayerMenuConverter());
        this.register(new LegacyChatFilterConverter(settingConverter));
        this.register(new LegacyLabyModuleConverter());
        this.registerAddonResolver(new DirectoryLegacyAddonResolver());
        this.registerAddonResolver(new CustomNameTagsAddonResolver());
        this.registerAddonResolver(new OptifineLegacyAddonResolver());
        try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("legacyAddons.json")) {
            if (inputStream == null) {
                DefaultLegacyConfigConverter.LOGGER.error("Cannot find legacy addons file on classpath", new Object[0]);
                if (inputStream != null) {
                    inputStream.close();
                }
                return;
            }
            try (final Reader reader = new InputStreamReader(inputStream)) {
                final JsonObject object = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)JsonObject.class);
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    final String namespace = entry.getKey();
                    for (final JsonElement legacyUuid : object.getAsJsonArray(namespace)) {
                        this.registerAddon(namespace, legacyUuid.getAsString());
                    }
                }
            }
        }
        catch (final IOException e) {
            DefaultLegacyConfigConverter.LOGGER.error("Failed to load legacy addons from classpath", e);
        }
    }
    
    @NotNull
    @Override
    public Collection<LegacyAddon> discoverLegacyAddons() {
        final Collection<LegacyAddon> addons = new ArrayList<LegacyAddon>();
        for (final LegacyAddonConverter.Version version : LegacyAddonConverter.Version.values()) {
            this.discoverLegacyAddons(version, addons);
        }
        return addons;
    }
    
    @NotNull
    @Override
    public Collection<LegacyAddon> discoverLegacyAddons(@NotNull final LegacyAddonConverter.Version version) {
        final Collection<LegacyAddon> addons = new ArrayList<LegacyAddon>();
        this.discoverLegacyAddons(version, addons);
        return addons;
    }
    
    private void discoverLegacyAddons(@NotNull final LegacyAddonConverter.Version version, @NotNull final Collection<LegacyAddon> addons) {
        for (final LegacyAddonResolver addonResolver : this.addonResolvers) {
            try {
                addonResolver.resolveAddons(version, addons);
            }
            catch (final IOException e) {
                DefaultLegacyConfigConverter.LOGGER.error("Failed to load legacy addons for version {} via resolver {}", version.getVersion(), addonResolver.getClass().getSimpleName(), e);
            }
        }
    }
    
    static {
        LOGGER = Logging.create(DefaultLegacyConfigConverter.class);
    }
}
