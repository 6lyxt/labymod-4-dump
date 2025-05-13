// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping;

import net.labymod.api.mapping.remap.JarRemapperBuilder;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import net.labymod.api.mapping.loader.MappingLoader;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.mapping.provider.MappingProvider;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.mapping.loader.MappingReader;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MappingService
{
    @NotNull
    default MappingService instance() {
        return Laby.references().mappingService();
    }
    
    @NotNull
    Collection<String> getNamespaces();
    
    void registerReader(@NotNull final MappingReader p0);
    
    @Nullable
    MappingReader findReader(@NotNull final MappingType p0);
    
    @NotNull
    MappingProvider registerMappings(@NotNull final MappingProvider p0);
    
    @NotNull
    MappingProvider registerMappings(@NotNull final InputStream p0, @NotNull final String p1, @NotNull final String p2, @NotNull final MappingType p3) throws IOException;
    
    @NotNull
    MappingProvider registerMappings(final MappingLoader p0) throws IOException;
    
    @Nullable
    MappingProvider mappings(@NotNull final String p0, @NotNull final String p1);
    
    @NotNull
    MappingProvider currentMappings();
    
    @NotNull
    IRemapper mixinRemapper(@NotNull final MappingProvider p0);
    
    @Nullable
    IRemapper mixinRemapper(@NotNull final String p0, @NotNull final String p1);
    
    @NotNull
    Remapper asmRemapper(@NotNull final MappingProvider p0);
    
    @Nullable
    Remapper asmRemapper(@NotNull final String p0, @NotNull final String p1);
    
    @NotNull
    JarRemapperBuilder jarRemapper(@NotNull final MappingProvider p0);
    
    @Nullable
    JarRemapperBuilder jarRemapper(@NotNull final String p0, @NotNull final String p1);
}
