// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.modloader;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.modloader.mod.ModInfo;
import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.models.version.Version;
import org.jetbrains.annotations.NotNull;

public interface ModLoader
{
    @NotNull
    String getId();
    
    @NotNull
    Version version();
    
    @NotNull
    String getExpectedMappingNamespace();
    
    @NotNull
    Collection<Path> getModDirectoryPaths();
    
    @NotNull
    Collection<Path> getConfigDirectoryPaths();
    
    @NotNull
    Collection<ModInfo> getModInfos();
    
    @Nullable
    ModInfo getModInfo(@NotNull final String p0);
    
    boolean isModLoaded(@NotNull final String p0);
}
