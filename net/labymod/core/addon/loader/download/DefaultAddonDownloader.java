// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.download;

import net.labymod.api.util.io.web.request.Response;
import java.util.Iterator;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.flint.FlintDefaultModifications;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class DefaultAddonDownloader extends AddonLoaderSubService
{
    private final FlintDefaultModifications defaultModifications;
    
    public DefaultAddonDownloader(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.DOWNLOAD);
        this.defaultModifications = FlintDefaultModifications.instance();
    }
    
    @Override
    public void handle() throws Exception {
        this.defaultModifications.loadDefaultAddons();
        if (Laby.references().launcherService().isUsingModPack() || LabyMod.getInstance().labyModLoader().isDevelopmentEnvironment()) {
            return;
        }
        for (String namespace : this.defaultModifications.getDefaultModifications()) {
            if (this.defaultModifications.hasInstalledBefore(namespace)) {
                continue;
            }
            if (this.isAddonInList(namespace)) {
                this.defaultModifications.install(namespace);
            }
            else {
                Response<AddonDownloadRequest.AddonDownloadResult> response;
                try {
                    response = AddonDownloadRequest.create().loadUniqueIdFromLoader().namespace(namespace).existsStrategy((requiredNamespace, existingInfo) -> false).ignoreUnsupported().executeSync();
                }
                catch (final IllegalArgumentException e) {
                    this.logError("Default addon " + namespace + " could now be found in the index", (Throwable)e);
                    continue;
                }
                if (response.hasException()) {
                    this.logError("Could not download default addon " + namespace, (Throwable)response.exception());
                }
                if (!response.isPresent()) {
                    continue;
                }
                final AddonDownloadRequest.AddonDownloadResult result = response.get();
                if (!result.hasSkippedMainAddon()) {
                    this.logger.info("Successfully downloaded default addon {}", namespace);
                }
                for (final InstalledAddonInfo addonInfo : result.getAddonInfos()) {
                    if (this.isAddonInList(addonInfo.getNamespace())) {
                        continue;
                    }
                    this.addAddon(addonInfo);
                    this.defaultModifications.install(addonInfo.getNamespace());
                }
            }
        }
    }
}
