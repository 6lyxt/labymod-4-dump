// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.pack;

import java.util.List;
import java.util.Collection;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourcePackRepository
{
    default void registerLoadedAddonPack(final LoadedAddon addon) {
        this.registerPack(addon.info().getNamespace(), addon);
    }
    
    default void registerLoadedAddonSilentPacket(final LoadedAddon addon) {
        this.registerSilentPack(addon.info().getNamespace(), addon);
    }
    
    void registerPack(final String p0);
    
    void registerPack(final String p0, final LoadedAddon p1);
    
    void registerSilentPack(final String p0);
    
    void registerSilentPack(final String p0, final LoadedAddon p1);
    
    void registerClientPack(final String p0, final String... p1);
    
    void registerPack(final ResourcePack p0);
    
    void registerSilentPack(final ResourcePack p0);
    
    boolean hasServerPackSelected();
    
    Collection<String> getSelectedPacks();
    
    List<ResourcePack> getRegisteredPacks();
    
    List<ResourcePackDetail> getAvailablePackDetails();
}
