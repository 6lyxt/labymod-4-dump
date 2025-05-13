// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.download;

import net.labymod.api.util.io.web.request.Response;
import java.util.Iterator;
import java.util.List;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import java.util.Collection;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonDependencyDownloader extends AddonLoaderSubService
{
    public AddonDependencyDownloader(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.DOWNLOAD);
    }
    
    @Override
    public void handle() throws Exception {
        final List<InstalledAddonInfo> addons = new ArrayList<InstalledAddonInfo>(this.getAddons());
        final List<String> installedAddons = new ArrayList<String>();
        for (final InstalledAddonInfo addon : addons) {
            installedAddons.add(addon.getNamespace());
        }
        final InstalledAddonInfo classpathAddon = this.addonLoader.classpathAddonLoader().getAddonInfo();
        if (classpathAddon != null) {
            installedAddons.add(classpathAddon.getNamespace());
        }
        for (InstalledAddonInfo addonInfo : addons) {
            Response<AddonDownloadRequest.AddonDownloadResult> response;
            try {
                response = AddonDownloadRequest.create().loadUniqueIdFromLoader().namespace(addonInfo.getNamespace()).path(addonInfo.getDirectoryPath(), addonInfo.getFileName()).downloadDependencies(true, (requiredNamespace, existingInfo) -> this.continueDownload(requiredNamespace, existingInfo, installedAddons)).existsStrategy((requiredNamespace, existingInfo) -> this.continueDownload(requiredNamespace, existingInfo, installedAddons)).skipDependencies(installedAddons).executeSync();
            }
            catch (final Exception e) {
                this.logError("Could not check dependencies of " + addonInfo.getNamespace(), (Throwable)e);
                continue;
            }
            if (response.hasException()) {
                this.logError("An error occured while downloading the dependencies of " + addonInfo.getNamespace(), (Throwable)response.exception());
            }
            if (response.isPresent()) {
                final AddonDownloadRequest.AddonDownloadResult result = response.get();
                for (final InstalledAddonInfo info : result.getAddonInfos()) {
                    final String namespace = info.getNamespace();
                    if (!this.isAddonInList(namespace)) {
                        this.logger.info("Successfully downloaded the dependency {}", namespace);
                        this.addAddon(info);
                    }
                    if (!installedAddons.contains(namespace)) {
                        installedAddons.add(namespace);
                    }
                }
            }
        }
    }
    
    private boolean continueDownload(final String requiredNamespace, final InstalledAddonInfo existingInfo, final List<String> installedAddons) {
        return !existingInfo.getNamespace().equals(requiredNamespace) && !installedAddons.contains(requiredNamespace);
    }
}
