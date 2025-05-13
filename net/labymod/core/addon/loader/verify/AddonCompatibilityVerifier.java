// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.verify;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.api.BuildData;
import net.labymod.core.addon.loader.AddonValidator;
import java.util.Collection;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonCompatibilityVerifier extends AddonLoaderSubService
{
    public AddonCompatibilityVerifier(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.VERIFY);
    }
    
    @Override
    public void handle() throws Exception {
        final Version version = this.labyModLoader.version();
        final List<InstalledAddonInfo> addons = new ArrayList<InstalledAddonInfo>(this.getAddons());
        for (final InstalledAddonInfo addonInfo : addons) {
            boolean unload = false;
            if (!addonInfo.getCompatibleMinecraftVersions().isCompatible(version)) {
                unload = true;
                this.logger.warn("Addon {} is not compatible with the current Minecraft version {}. Unloading...", addonInfo.getNamespace(), version);
            }
            else if (AddonValidator.isBuildNumberGreater(addonInfo)) {
                unload = true;
                this.logger.warn("Addon {} requires a newer LabyMod build ({}, current is {}). Unloading...", addonInfo.getNamespace(), addonInfo.getRequiredLabyModBuild(), BuildData.getBuildNumber());
            }
            if (unload) {
                this.getAddons().remove(addonInfo);
            }
        }
    }
}
