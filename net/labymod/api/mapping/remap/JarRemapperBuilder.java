// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.remap;

import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public interface JarRemapperBuilder
{
    @NotNull
    JarRemapperBuilder entry(@NotNull final Path p0, @NotNull final Path p1);
    
    @NotNull
    JarRemapperBuilder entry(@NotNull final JarRemapEntry p0);
    
    @NotNull
    JarRemapperBuilder library(@NotNull final Path p0);
    
    @NotNull
    JarRemapper build();
}
