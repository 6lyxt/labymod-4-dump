// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.modloader.mod;

import java.util.Collection;
import net.labymod.api.models.version.Version;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ModInfo
{
    @Nullable
    String getId();
    
    @NotNull
    String getLoaderId();
    
    @NotNull
    Version version();
    
    @NotNull
    String getDisplayName();
    
    @NotNull
    String getDescription();
    
    @NotNull
    Collection<String> getAuthors();
    
    @NotNull
    Collection<String> getContributors();
    
    @NotNull
    Collection<ModDependency> getDependencies();
}
