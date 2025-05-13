// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.remap;

import net.labymod.api.mapping.remap.JarRemapper;
import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.nio.file.Path;
import net.labymod.api.mapping.remap.JarRemapEntry;
import java.util.Collection;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.labymod.api.mapping.remap.JarRemapperBuilder;

public class FartJarRemapperBuilder implements JarRemapperBuilder
{
    private final FartMappingProvider mappingProvider;
    private final Collection<JarRemapEntry> entries;
    private final Collection<Path> libraries;
    
    public FartJarRemapperBuilder(final FartMappingProvider mappingProvider) {
        this.mappingProvider = mappingProvider;
        this.entries = new HashSet<JarRemapEntry>();
        this.libraries = new HashSet<Path>();
    }
    
    @NotNull
    @Override
    public JarRemapperBuilder entry(@NotNull final Path inputPath, @NotNull final Path outputPath) {
        return this.entry(new JarRemapEntry(inputPath, outputPath));
    }
    
    @NotNull
    @Override
    public JarRemapperBuilder entry(@NotNull final JarRemapEntry entry) {
        this.entries.add(entry);
        return this;
    }
    
    @NotNull
    @Override
    public JarRemapperBuilder library(@NotNull final Path libraryPath) {
        this.libraries.add(libraryPath);
        return this;
    }
    
    @NotNull
    @Override
    public JarRemapper build() {
        return new FartJarRemapper(this.mappingProvider, this.entries, this.libraries);
    }
}
