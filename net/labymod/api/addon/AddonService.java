// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public interface AddonService
{
    @NotNull
    Optional<LoadedAddon> getAddon(final String p0);
    
    Optional<LoadedAddon> getOptionalAddon(final String p0);
    
    @NotNull
    default Optional<LoadedAddon> getAddon(@NotNull final Class<?> clazz) {
        return this.getAddon(clazz.getClassLoader());
    }
    
    @NotNull
    Optional<LoadedAddon> getAddon(final ClassLoader p0);
    
    Collection<LoadedAddon> getLoadedAddons();
    
    Collection<LoadedAddon> getVisibleAddons();
    
    @Nullable
    InstalledAddonInfo getAddonInfo(final String p0);
    
    Class<?> loadClassFromAddon(final String p0) throws ClassNotFoundException;
    
    LoadedAddon getLastCallerAddon();
    
    @Nullable
    String getClassPathAddon();
    
    boolean isEnabled(final Object p0);
    
    boolean isEnabled(final Class<?> p0);
    
    boolean isEnabled(final String p0);
    
    boolean isEnabled(final InstalledAddonInfo p0, final boolean p1);
    
    boolean isForceDisabled(final String p0);
    
    @ApiStatus.Internal
    void registerMainConfiguration(final String p0, final AddonConfig p1);
    
    @ApiStatus.Internal
    boolean hasMainConfiguration(final String p0);
}
