// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.remap;

import java.io.IOException;
import net.minecraftforge.fart.api.SignatureStripperConfig;
import net.minecraftforge.fart.api.Renamer;
import net.labymod.api.mapping.MappingService;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.provider.MappingProvider;
import java.nio.file.Paths;
import net.labymod.api.Constants;
import java.util.Locale;
import java.util.HashSet;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.minecraftforge.fart.api.Transformer;
import java.nio.file.Path;
import net.labymod.api.mapping.remap.JarRemapEntry;
import java.util.Collection;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.labymod.api.mapping.remap.JarRemapper;

public class FartJarRemapper implements JarRemapper
{
    private final FartMappingProvider mappingProvider;
    private final Collection<JarRemapEntry> entries;
    private final Collection<Path> libraries;
    private final Collection<Transformer> transformers;
    private final Path sourceRemapJarPath;
    
    public FartJarRemapper(final FartMappingProvider mappingProvider, final Collection<JarRemapEntry> entries, final Collection<Path> libraries) {
        this.mappingProvider = mappingProvider;
        this.entries = entries;
        this.libraries = libraries;
        final Path obfuscatedJarPath = PlatformEnvironment.getObfuscatedJarPath();
        if (obfuscatedJarPath == null || !Files.exists(obfuscatedJarPath, new LinkOption[0])) {
            throw new IllegalStateException("Cannot perform remap without obfuscated minecraft jar");
        }
        this.transformers = new HashSet<Transformer>();
        final String sourceNamespace = mappingProvider.getSourceNamespace();
        this.sourceRemapJarPath = ("official".equals(sourceNamespace) ? obfuscatedJarPath : Paths.get(String.format(Locale.ROOT, Constants.Files.REMAP_JAR_PATH, PlatformEnvironment.getRunningVersion(), sourceNamespace), new String[0]));
    }
    
    public void addTransformer(final Transformer transformer) {
        this.transformers.add(transformer);
    }
    
    @NotNull
    @Override
    public MappingProvider mappingProvider() {
        return this.mappingProvider;
    }
    
    @NotNull
    @Override
    public Collection<JarRemapEntry> getRemapEntries() {
        return this.entries;
    }
    
    @Nullable
    @Override
    public Path findOutputPath(@NotNull final Path inputPath) {
        for (final JarRemapEntry entry : this.entries) {
            if (entry.getInputPath().equals(inputPath)) {
                return entry.getOutputPath();
            }
        }
        return null;
    }
    
    @NotNull
    @Override
    public StringBuilder execute() throws IOException {
        final StringBuilder logBuilder = new StringBuilder();
        if (!Files.exists(this.sourceRemapJarPath, new LinkOption[0])) {
            final MappingProvider provider = MappingService.instance().mappings("official", this.mappingProvider.getSourceNamespace());
            if (provider == null) {
                throw new IllegalStateException("Source mappings are not registered");
            }
            final StringBuilder childLogBuilder = MappingService.instance().jarRemapper(provider).entry(PlatformEnvironment.getObfuscatedJarPath(), this.sourceRemapJarPath).build().execute();
            logBuilder.append((CharSequence)childLogBuilder);
        }
        final Renamer.Builder renamerBuilder = Renamer.builder().logger(line -> logBuilder.append(line).append(System.lineSeparator())).add(Transformer.renamerFactory(this.mappingProvider.getDelegate(), false)).add(Transformer.signatureStripperFactory(SignatureStripperConfig.ALL)).lib(this.sourceRemapJarPath.toFile());
        for (final Transformer transformer : this.transformers) {
            renamerBuilder.add(transformer);
        }
        for (final Path lib : this.libraries) {
            renamerBuilder.lib(lib.toFile());
        }
        try (final Renamer renamer = renamerBuilder.build()) {
            for (final JarRemapEntry entry : this.entries) {
                Files.deleteIfExists(entry.getOutputPath());
                renamer.run(entry.getInputPath().toFile(), entry.getOutputPath().toFile());
            }
        }
        return logBuilder;
    }
}
