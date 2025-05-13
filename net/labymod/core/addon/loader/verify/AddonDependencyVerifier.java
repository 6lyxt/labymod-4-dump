// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.verify;

import net.labymod.api.models.addon.info.dependency.AddonDependency;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonDependencyVerifier extends AddonLoaderSubService
{
    public AddonDependencyVerifier(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.VERIFY);
    }
    
    @Override
    public void handle() throws Exception {
        final List<InstalledAddonInfo> addonsAboutToBeUnloaded = new ArrayList<InstalledAddonInfo>();
        for (final InstalledAddonInfo addonInfo : this.getAddons()) {
            if (addonsAboutToBeUnloaded.contains(addonInfo)) {
                continue;
            }
            this.isLoadable(addonInfo, addonsAboutToBeUnloaded);
        }
        for (final InstalledAddonInfo addonInfo : addonsAboutToBeUnloaded) {
            this.getAddons().remove(addonInfo);
        }
    }
    
    private boolean isLoadable(final InstalledAddonInfo addonInfo, final List<InstalledAddonInfo> addonsAboutToBeUnloaded) {
        final AddonDependency[] dependencies = addonInfo.getCompatibleAddonDependencies(this.labyModLoader.version());
        if (dependencies == null || dependencies.length == 0) {
            return true;
        }
        final List<String> missingDependencies = new ArrayList<String>();
        for (final AddonDependency dependency : dependencies) {
            if (!dependency.isOptional()) {
                final InstalledAddonInfo dependencyInfo = this.getAddon(dependency.getNamespace());
                if (dependencyInfo == null || !this.isLoadable(dependencyInfo, addonsAboutToBeUnloaded)) {
                    missingDependencies.add(dependency.getNamespace());
                }
            }
        }
        if (!missingDependencies.isEmpty()) {
            if (!addonsAboutToBeUnloaded.contains(addonInfo)) {
                this.logger.info("Unloading addon {} because of missing dependencies. ({})", addonInfo.getNamespace(), String.join(", ", missingDependencies));
                addonsAboutToBeUnloaded.add(addonInfo);
            }
            return false;
        }
        return true;
    }
}
