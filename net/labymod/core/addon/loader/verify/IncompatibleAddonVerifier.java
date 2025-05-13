// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.verify;

import java.util.function.Consumer;
import java.util.Collection;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.AddonMeta;
import java.util.Iterator;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherIncompatibleAddonPacket;
import net.labymod.api.Laby;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.List;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class IncompatibleAddonVerifier extends AddonLoaderSubService
{
    private final List<InstalledAddonInfo> incompatibleAddons;
    
    public IncompatibleAddonVerifier(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.VERIFY);
        this.incompatibleAddons = new ArrayList<InstalledAddonInfo>();
    }
    
    @Override
    public void handle() throws Exception {
        final DefaultLauncherService launcherService = (DefaultLauncherService)Laby.references().launcherService();
        if (launcherService.isConnectedToLauncher()) {
            final List<LauncherIncompatibleAddonPacket.IncompatibleAddon> incompatible = new ArrayList<LauncherIncompatibleAddonPacket.IncompatibleAddon>();
            final Iterator<InstalledAddonInfo> iterator = this.getAddons().iterator();
            InstalledAddonInfo addon = null;
            while (iterator.hasNext()) {
                addon = iterator.next();
                final String[] incompatibleAddons = addon.getIncompatibleAddons();
                if (incompatibleAddons != null) {
                    if (incompatibleAddons.length == 0) {
                        continue;
                    }
                    final List<String> dependenciesList = new ArrayList<String>();
                    this.gatherDependingAddons(addon, dependenciesList);
                    final String[] dependencies = dependenciesList.toArray(new String[0]);
                    final List<String> incompatibleDependencies = new ArrayList<String>();
                    for (final String incompatibleAddon : incompatibleAddons) {
                        final InstalledAddonInfo incompatibleAddonInfo = this.getAddon(incompatibleAddon);
                        if (incompatibleAddonInfo != null) {
                            if (!incompatibleDependencies.contains(incompatibleAddon)) {
                                incompatibleDependencies.add(incompatibleAddon);
                            }
                            this.gatherDependingAddons(incompatibleAddonInfo, incompatibleDependencies);
                        }
                    }
                    incompatible.add(new LauncherIncompatibleAddonPacket.IncompatibleAddon(addon.getNamespace(), dependencies, incompatibleDependencies.toArray(new String[0])));
                }
            }
            if (incompatible.isEmpty()) {
                return;
            }
            final LauncherIncompatibleAddonPacket launcherIncompatibleAddonPacket = launcherService.getCommunicator().sendPacket(new LauncherIncompatibleAddonPacket(incompatible), LauncherIncompatibleAddonPacket.class);
            if (launcherIncompatibleAddonPacket != null) {
                for (final LauncherIncompatibleAddonPacket.IncompatibleAddon incompatibleAddon2 : launcherIncompatibleAddonPacket.getIncompatibleAddons()) {
                    final InstalledAddonInfo addon2 = this.getAddon(incompatibleAddon2.getNamespace());
                    if (addon2 == null) {
                        continue;
                    }
                    if (incompatibleAddon2.disableIncompatible()) {
                        this.disableIncompatibleAddons(addon2, incompatibleNamespace -> this.logger.info("Addon {} is incompatible with addon {} and User decided against the incompatibilities. Unloading {}...", addon.getNamespace(), incompatibleNamespace, incompatibleNamespace));
                    }
                    else {
                        this.getAddons().remove(addon2);
                        this.incompatibleAddons.add(addon2);
                        this.logger.info("User decided to remove addon {}, due to incompatibilities with other addons. Unloading {}...", addon2.getNamespace(), addon2.getNamespace());
                    }
                }
                return;
            }
        }
        this.disableIncompatibleAddonsFallback();
    }
    
    public List<InstalledAddonInfo> getIncompatibleAddons() {
        return this.incompatibleAddons;
    }
    
    private void gatherDependingAddons(final InstalledAddonInfo info, final List<String> dependencies) {
        for (final InstalledAddonInfo addon : this.getAddons()) {
            final String namespace = addon.getNamespace();
            if (!namespace.equals(info.getNamespace())) {
                if (addon.hasMeta(AddonMeta.HIDDEN)) {
                    continue;
                }
                final AddonDependency[] compatibleAddonDependencies2;
                final AddonDependency[] compatibleAddonDependencies = compatibleAddonDependencies2 = addon.getCompatibleAddonDependencies(this.labyModLoader.version());
                for (final AddonDependency compatibleAddonDependency : compatibleAddonDependencies2) {
                    final String dependencyNamespace = compatibleAddonDependency.getNamespace();
                    if (!compatibleAddonDependency.isOptional()) {
                        if (dependencyNamespace.equals(info.getNamespace())) {
                            if (!dependencies.contains(namespace)) {
                                dependencies.add(namespace);
                            }
                            this.gatherDependingAddons(addon, dependencies);
                        }
                    }
                }
            }
        }
    }
    
    private void disableIncompatibleAddonsFallback() {
        final List<InstalledAddonInfo> addons = new ArrayList<InstalledAddonInfo>(this.getAddons());
        for (final InstalledAddonInfo addonInfo : addons) {
            this.disableIncompatibleAddons(addonInfo, incompatible -> this.logger.warn("Addon {} is incompatible with addon {}. Unloading {}...", addonInfo.getNamespace(), incompatible, incompatible));
        }
    }
    
    private void disableIncompatibleAddons(final InstalledAddonInfo addonInfo, final Consumer<String> logger) {
        final String[] incompatibleAddons = addonInfo.getIncompatibleAddons();
        if (incompatibleAddons == null) {
            return;
        }
        for (final String incompatibleAddon : incompatibleAddons) {
            final InstalledAddonInfo incompatibleAddonInfo = this.getAddon(incompatibleAddon);
            if (incompatibleAddonInfo != null) {
                this.incompatibleAddons.add(incompatibleAddonInfo);
                this.getAddons().remove(incompatibleAddonInfo);
                logger.accept(incompatibleAddonInfo.getNamespace());
            }
        }
    }
}
