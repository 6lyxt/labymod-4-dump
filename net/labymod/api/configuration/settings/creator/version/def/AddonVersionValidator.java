// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.version.def;

import java.util.Optional;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonService;
import net.labymod.api.configuration.settings.creator.version.VersionValidator;

public class AddonVersionValidator extends VersionValidator
{
    private final AddonService addonService;
    
    public AddonVersionValidator(final LabyAPI labyAPI) {
        super(labyAPI);
        this.addonService = labyAPI.addonService();
    }
    
    @Override
    public boolean isSupportedVersion(final String version, final String namespace) {
        final Optional<LoadedAddon> selectedAddon = this.addonService.getAddon(namespace);
        if (selectedAddon.isEmpty()) {
            return false;
        }
        final LoadedAddon loadedAddon = selectedAddon.get();
        return this.compareVersion(loadedAddon.info().getVersion(), version);
    }
}
