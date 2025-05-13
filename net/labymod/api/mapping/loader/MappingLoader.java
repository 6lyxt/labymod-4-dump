// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.loader;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.mapping.MappingType;
import org.jetbrains.annotations.NotNull;

public interface MappingLoader
{
    @NotNull
    String getSourceNamespace();
    
    @NotNull
    String getTargetNamespace();
    
    @NotNull
    MappingType type();
    
    @NotNull
    InputStream load() throws IOException;
}
