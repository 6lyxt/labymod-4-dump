// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.remap;

import java.io.IOException;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.provider.MappingProvider;

public interface JarRemapper
{
    @NotNull
    MappingProvider mappingProvider();
    
    @NotNull
    Collection<JarRemapEntry> getRemapEntries();
    
    @Nullable
    Path findOutputPath(@NotNull final Path p0);
    
    @NotNull
    StringBuilder execute() throws IOException;
}
