// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.loader;

import java.io.IOException;
import net.labymod.api.mapping.provider.MappingProvider;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.MappingType;

public interface MappingReader
{
    @NotNull
    MappingType[] supportedTypes();
    
    @NotNull
    MappingProvider read(@NotNull final InputStream p0, @NotNull final String p1, @NotNull final String p2, @NotNull final MappingType p3) throws IOException;
}
