// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.modloader.mod;

import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionComparison;
import org.jetbrains.annotations.NotNull;

public interface ModDependency
{
    @NotNull
    String getModId();
    
    @NotNull
    VersionComparison<Version> versionCompatibility();
    
    @NotNull
    Kind kind();
    
    public enum Kind
    {
        DEPENDS, 
        RECOMMENDS, 
        SUGGESTS, 
        CONFLICTS, 
        BREAKS;
    }
}
