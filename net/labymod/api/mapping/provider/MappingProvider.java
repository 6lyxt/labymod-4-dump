// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.provider;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.mapping.provider.child.ClassMapping;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public interface MappingProvider
{
    @NotNull
    String getSourceNamespace();
    
    @NotNull
    String getTargetNamespace();
    
    @NotNull
    Collection<ClassMapping> getClassMappings();
    
    @Nullable
    ClassMapping getClassMapping(@Nullable final String p0);
    
    @NotNull
    String mapClass(@NotNull final String p0);
    
    @NotNull
    String mapDescriptor(@NotNull final String p0);
    
    @NotNull
    MappingProvider reverse();
    
    @NotNull
    MappingProvider chain(@NotNull final MappingProvider p0);
    
    @NotNull
    MappingProvider sourceMappings();
    
    @NotNull
    MappingProvider targetMappings();
}
