// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.converter;

import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LegacyConfigConverter
{
    void register(@NotNull final LegacyConverter<?> p0);
    
    void registerAddon(@NotNull final String p0, @NotNull final String... p1);
    
    void registerAddonResolver(@NotNull final LegacyAddonResolver p0);
    
    @Nullable
    String getModernAddonNamespace(@NotNull final String p0);
    
    @NotNull
    Collection<LegacyConverter<?>> getConverters();
    
    @NotNull
    Collection<LegacyConverter<?>> getConverters(@NotNull final String p0);
    
    boolean hasLegacyInstallation();
    
    boolean hasStuffToConvert(@NotNull final String p0);
    
    boolean wasConversionAsked(@NotNull final String p0);
    
    void setConversionAsked(@NotNull final String p0);
    
    void useVersion(@NotNull final String p0, @NotNull final LegacyAddonConverter.Version p1);
    
    int convert(@NotNull final String p0);
    
    @NotNull
    Collection<LegacyAddon> discoverLegacyAddons();
    
    @NotNull
    Collection<LegacyAddon> discoverLegacyAddons(@NotNull final LegacyAddonConverter.Version p0);
}
