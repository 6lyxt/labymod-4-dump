// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter.addon;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.converter.LegacyAddonConverter;

public interface LegacyAddon
{
    @NotNull
    LegacyAddonConverter.Version getVersion();
    
    @NotNull
    String getName();
    
    @Nullable
    String getNamespace();
}
