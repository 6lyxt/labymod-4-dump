// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.provider.child;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

public interface ClassMapping extends Mapping
{
    @NotNull
    Collection<FieldMapping> getFieldMappings();
    
    @Nullable
    FieldMapping getFieldMapping(@Nullable final String p0);
    
    @NotNull
    Collection<MethodMapping> getMethodMappings();
    
    @Nullable
    MethodMapping getMethodMapping(@Nullable final String p0, @Nullable final String p1);
    
    @NotNull
    String mapField(@NotNull final String p0);
    
    @NotNull
    String mapMethod(@NotNull final String p0, @NotNull final String p1);
}
