// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.download;

import net.labymod.api.util.io.web.request.Response;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.loader.initial.AdditionalAddonLoader;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AdditionalAddonDownloader extends AddonLoaderSubService
{
    private final AdditionalAddonLoader additionalAddonLoader;
    
    public AdditionalAddonDownloader(final AddonLoader addonLoader, final AdditionalAddonLoader additionalAddonLoader) {
        super(addonLoader, SubServiceStage.DOWNLOAD);
        this.additionalAddonLoader = additionalAddonLoader;
    }
    
    @Override
    public void handle() throws Exception {
        final List<String> missingAdditionalAddons = this.additionalAddonLoader.getMissingAdditionalAddons();
        if (missingAdditionalAddons.isEmpty()) {
            return;
        }
        for (String namespace : missingAdditionalAddons) {
            if (this.isAddonInList(namespace)) {
                continue;
            }
            Response<AddonDownloadRequest.AddonDownloadResult> response;
            try {
                response = AddonDownloadRequest.create().loadUniqueIdFromLoader().namespace(namespace).path(AdditionalAddonLoader.ADDITIONAL_ADDONS_DIRECTORY, namespace + ".jar").existsStrategy((requiredNamespace, existingInfo) -> {
                    if (existingInfo == null) {
                        return true;
                    }
                    else {
                        return !existingInfo.getNamespace().equalsIgnoreCase(requiredNamespace);
                    }
                }).ignoreUnsupported().executeSync();
            }
            catch (final IllegalArgumentException e) {
                this.logError("Additional addon " + namespace + " could now be found in the index", (Throwable)e);
                continue;
            }
            if (response.hasException()) {
                this.logError("Could not download additional addon " + namespace, (Throwable)response.exception());
            }
            if (!response.isPresent()) {
                continue;
            }
            final AddonDownloadRequest.AddonDownloadResult result = response.get();
            if (!result.hasSkippedMainAddon()) {
                this.logger.info("Successfully downloaded additional addon {}", namespace);
            }
            for (final InstalledAddonInfo addonInfo : result.getAddonInfos()) {
                if (this.isAddonInList(addonInfo.getNamespace())) {
                    continue;
                }
                this.addAddon(addonInfo);
            }
        }
    }
}
