// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping;

import net.labymod.core.mapping.remap.FartJarRemapperBuilder;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.labymod.api.mapping.remap.JarRemapperBuilder;
import net.labymod.core.mapping.remap.AsmRemapper;
import org.objectweb.asm.commons.Remapper;
import net.labymod.core.mapping.remap.MixinRemapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import net.labymod.core.loader.DefaultLabyModLoader;
import java.io.InputStream;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import net.labymod.core.mapping.loader.mojang.MojangMappingLoader;
import net.labymod.core.mapping.loader.mcp.McpMappingLoader;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.core.mapping.loader.mcp.SeargeMappingLoader;
import net.labymod.core.mapping.loader.mcp.McpConfig;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.mapping.loader.FartMappingReader;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import net.labymod.api.mapping.provider.MappingProvider;
import java.util.Map;
import net.labymod.api.mapping.loader.MappingReader;
import java.util.Collection;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.service.Service;

@Singleton
@Implements(MappingService.class)
public class FartMappingService extends Service implements MappingService
{
    private final Collection<MappingReader> mappingReaders;
    private final Map<String, MappingProvider> mappingProviders;
    private final Map<String, MappingProvider> cachedMappings;
    
    @Inject
    public FartMappingService() {
        this.mappingReaders = new HashSet<MappingReader>();
        this.mappingProviders = new HashMap<String, MappingProvider>();
        this.cachedMappings = new HashMap<String, MappingProvider>();
    }
    
    @Override
    protected void prepare() {
        try {
            if (!IOUtil.exists(Constants.Files.MAPPINGS)) {
                IOUtil.createDirectories(Constants.Files.MAPPINGS);
            }
        }
        catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        this.registerReader(new FartMappingReader());
        if (PlatformEnvironment.isNoMojangMappings()) {
            McpConfig mcpConfig = null;
            for (final McpConfig value : McpConfig.values()) {
                if (value.getMinecraftVersion().equals(PlatformEnvironment.getRunningVersion())) {
                    mcpConfig = value;
                    break;
                }
            }
            if (mcpConfig != null) {
                try {
                    final SeargeMappingLoader seargeMappingLoader = new SeargeMappingLoader(mcpConfig);
                    this.registerMappings(seargeMappingLoader);
                    final MappingProvider mappings = this.registerMappings(new McpMappingLoader(seargeMappingLoader));
                    this.registerMappings(mappings.sourceMappings());
                }
                catch (final Exception exception2) {
                    throw new RuntimeException("Failed to register MCP mappings", exception2);
                }
            }
        }
        else {
            try {
                final MappingProvider mappings2 = this.registerMappings(new MojangMappingLoader());
                this.registerMappings(mappings2.sourceMappings());
            }
            catch (final Exception exception3) {
                throw new RuntimeException("Failed to register Mojang mappings", exception3);
            }
        }
    }
    
    @NotNull
    @Override
    public Collection<String> getNamespaces() {
        return Collections.unmodifiableCollection((Collection<? extends String>)this.mappingProviders.keySet());
    }
    
    @Override
    public void registerReader(@NotNull final MappingReader reader) {
        this.mappingReaders.add(reader);
    }
    
    @Nullable
    @Override
    public MappingReader findReader(@NotNull final MappingType type) {
        for (final MappingReader mappingReader : this.mappingReaders) {
            for (final MappingType mappingType : mappingReader.supportedTypes()) {
                if (mappingType == type) {
                    return mappingReader;
                }
            }
        }
        return null;
    }
    
    @NotNull
    @Override
    public MappingProvider registerMappings(@NotNull final InputStream stream, @NotNull final String sourceNamespace, @NotNull final String targetNamespace, @NotNull final MappingType type) throws IOException {
        final MappingReader reader = this.findReader(type);
        if (reader == null) {
            throw new IllegalStateException("No mapping reader registered for type " + String.valueOf(type));
        }
        final MappingProvider provider = reader.read(stream, sourceNamespace, targetNamespace, type);
        return this.registerMappings(provider);
    }
    
    @NotNull
    @Override
    public MappingProvider registerMappings(final MappingLoader loader) throws IOException {
        try (final InputStream stream = loader.load()) {
            return this.registerMappings(stream, loader.getSourceNamespace(), loader.getTargetNamespace(), loader.type());
        }
    }
    
    @NotNull
    @Override
    public MappingProvider registerMappings(@NotNull MappingProvider provider) {
        if (!provider.getSourceNamespace().equals("official") && !provider.getTargetNamespace().equals("official")) {
            throw new IllegalArgumentException("Provided mappings do not contain the obfuscated namespace");
        }
        if (provider.getTargetNamespace().equals("official")) {
            provider = provider.reverse();
        }
        this.mappingProviders.put(provider.getTargetNamespace(), provider);
        return provider;
    }
    
    @Nullable
    @Override
    public MappingProvider mappings(@NotNull final String sourceNamespace, @NotNull final String targetNamespace) {
        final String cacheKey = sourceNamespace + targetNamespace;
        if (this.cachedMappings.containsKey(cacheKey)) {
            return this.cachedMappings.get(cacheKey);
        }
        final MappingProvider sourceProvider = this.mappingProviders.get(sourceNamespace);
        final MappingProvider targetProvider = this.mappingProviders.get(targetNamespace);
        MappingProvider mappings = null;
        if (sourceProvider == null && targetProvider != null) {
            mappings = (targetProvider.getSourceNamespace().equals(sourceNamespace) ? targetProvider : null);
        }
        else if (targetProvider == null && sourceProvider != null) {
            mappings = (sourceProvider.getSourceNamespace().equals(targetNamespace) ? sourceProvider.reverse() : null);
        }
        else if (sourceProvider != null) {
            mappings = sourceProvider.reverse().chain(targetProvider);
        }
        this.cachedMappings.put(cacheKey, mappings);
        return mappings;
    }
    
    @NotNull
    @Override
    public MappingProvider currentMappings() {
        final String currentNamespace = DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment() ? "named" : "official";
        final MappingProvider provider = this.mappings("named", currentNamespace);
        if (provider == null) {
            throw new IllegalStateException("Current mappings are not available");
        }
        return provider;
    }
    
    @Nullable
    @Override
    public IRemapper mixinRemapper(@NotNull final String sourceNamespace, @NotNull final String targetNamespace) {
        final MappingProvider provider = this.mappings(sourceNamespace, targetNamespace);
        return (provider == null) ? null : this.mixinRemapper(provider);
    }
    
    @NotNull
    @Override
    public IRemapper mixinRemapper(@NotNull final MappingProvider provider) {
        return (IRemapper)new MixinRemapper(provider);
    }
    
    @Nullable
    @Override
    public Remapper asmRemapper(@NotNull final String sourceNamespace, @NotNull final String targetNamespace) {
        final MappingProvider provider = this.mappings(sourceNamespace, targetNamespace);
        return (provider == null) ? null : this.asmRemapper(provider);
    }
    
    @NotNull
    @Override
    public Remapper asmRemapper(@NotNull final MappingProvider provider) {
        return new AsmRemapper(provider);
    }
    
    @Override
    public JarRemapperBuilder jarRemapper(@NotNull final String sourceNamespace, @NotNull final String targetNamespace) {
        final MappingProvider provider = this.mappings(sourceNamespace, targetNamespace);
        return (provider == null) ? null : this.jarRemapper(provider);
    }
    
    @NotNull
    @Override
    public JarRemapperBuilder jarRemapper(@NotNull final MappingProvider provider) {
        if (!(provider instanceof FartMappingProvider)) {
            throw new IllegalStateException("Cannot remap with non-FartMappingProvider");
        }
        return new FartJarRemapperBuilder((FartMappingProvider)provider);
    }
}
