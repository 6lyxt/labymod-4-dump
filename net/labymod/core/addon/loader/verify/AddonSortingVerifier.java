// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.verify;

import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.HashMap;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonSortingVerifier extends AddonLoaderSubService
{
    public AddonSortingVerifier(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.VERIFY);
    }
    
    @Override
    public void handle() throws Exception {
        final Map<InstalledAddonInfo, AddonDependency[]> addons = new HashMap<InstalledAddonInfo, AddonDependency[]>();
        for (final InstalledAddonInfo addonInfo : this.getAddons()) {
            addons.put(addonInfo, addonInfo.getCompatibleAddonDependencies(this.labyModLoader.version()));
        }
        final Map<String, List<String>> graph = new HashMap<String, List<String>>();
        for (final Map.Entry<InstalledAddonInfo, AddonDependency[]> entry : addons.entrySet()) {
            final List<String> dependencies = new ArrayList<String>();
            for (final AddonDependency dependency : entry.getValue()) {
                dependencies.add(dependency.getNamespace());
            }
            graph.put(entry.getKey().getNamespace(), dependencies);
        }
        final Set<String> visited = new HashSet<String>();
        final List<String> sortedAddons = new ArrayList<String>();
        for (final InstalledAddonInfo installedAddonInfo : addons.keySet()) {
            if (!visited.contains(installedAddonInfo.getNamespace())) {
                this.dfs(installedAddonInfo.getNamespace(), graph, visited, sortedAddons);
            }
        }
        final List<InstalledAddonInfo> sortedAddonsList = new ArrayList<InstalledAddonInfo>();
        for (final String sortedAddon : sortedAddons) {
            for (final InstalledAddonInfo installedAddonInfo2 : addons.keySet()) {
                if (installedAddonInfo2.getNamespace().equals(sortedAddon)) {
                    sortedAddonsList.add(installedAddonInfo2);
                    break;
                }
            }
        }
        this.getAddons().clear();
        this.getAddons().addAll(sortedAddonsList);
    }
    
    private void dfs(final String addon, final Map<String, List<String>> graph, final Set<String> visited, final List<String> sortedAddons) {
        visited.add(addon);
        for (final String dependency : graph.getOrDefault(addon, Collections.emptyList())) {
            if (!visited.contains(dependency)) {
                this.dfs(dependency, graph, visited, sortedAddons);
            }
        }
        sortedAddons.add(addon);
    }
}
