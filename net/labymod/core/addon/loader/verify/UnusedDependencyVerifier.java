// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.verify;

import net.labymod.api.models.addon.info.dependency.AddonDependency;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.models.addon.info.AddonMeta;
import java.util.Collection;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class UnusedDependencyVerifier extends AddonLoaderSubService
{
    private final IncompatibleAddonVerifier incompatibleAddonVerifier;
    
    public UnusedDependencyVerifier(final AddonLoader addonLoader, final IncompatibleAddonVerifier incompatibleAddonVerifier) {
        super(addonLoader, SubServiceStage.VERIFY);
        this.incompatibleAddonVerifier = incompatibleAddonVerifier;
    }
    
    @Override
    public void handle() throws Exception {
        final Version version = this.labyModLoader.version();
        final List<String> requiredDependencies = new ArrayList<String>();
        final Iterator<InstalledAddonInfo> iterator = this.getAddons().iterator();
        InstalledAddonInfo addon = null;
        AddonDependency dependency = null;
        while (iterator.hasNext()) {
            addon = iterator.next();
            final AddonDependency[] compatibleAddonDependencies = addon.getCompatibleAddonDependencies(version);
            for (int length = compatibleAddonDependencies.length, i = 0; i < length; ++i) {
                dependency = compatibleAddonDependencies[i];
                if (!dependency.isOptional()) {
                    if (!requiredDependencies.contains(dependency.getNamespace())) {
                        requiredDependencies.add(dependency.getNamespace());
                    }
                }
            }
        }
        this.logger.info("Filtering out unused dependencies", new Object[0]);
        final List<InstalledAddonInfo> addons = new ArrayList<InstalledAddonInfo>(this.getAddons());
        for (InstalledAddonInfo addon2 : addons) {
            if (addon2.hasMeta(AddonMeta.HIDDEN)) {
                if (requiredDependencies.contains(addon2.getNamespace())) {
                    continue;
                }
                this.logger.info("Removing unused dependency {}", addon2.getNamespace());
                this.getAddons().remove(addon2);
                if (CollectionHelper.anyMatch(this.incompatibleAddonVerifier.getIncompatibleAddons(), incompatibleAddon -> CollectionHelper.anyMatch(incompatibleAddon.getCompatibleAddonDependencies(version), dependency -> dependency.getNamespace().equals(addon.getNamespace())))) {
                    this.logger.info("Won't delete unused dependency {} as it is used by an incompatible addon", addon2.getNamespace());
                }
                else {
                    try {
                        IOUtil.delete(addon2.getPath());
                    }
                    catch (final Exception e) {
                        this.logError("Could not delete unused dependency " + addon2.getNamespace(), (Throwable)e);
                    }
                }
            }
        }
    }
}
