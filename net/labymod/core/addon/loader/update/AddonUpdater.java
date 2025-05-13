// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.loader.update;

import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Response;
import java.util.Iterator;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import java.nio.file.Files;
import net.labymod.api.BuildData;
import net.labymod.core.flint.FlintUrls;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.AbstractRequest;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import net.labymod.core.addon.AddonLoader;
import java.util.Map;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.List;
import net.labymod.core.addon.loader.AddonLoaderSubService;

public class AddonUpdater extends AddonLoaderSubService
{
    private static final String INVALIDATED_STRING = "INVALIDATED";
    private static final String DELETED_STRING = "DELETED";
    private final List<InstalledAddonInfo> invalidated;
    private final List<InstalledAddonInfo> deleted;
    private final Map<String, UpdateData> updates;
    
    public AddonUpdater(final AddonLoader addonLoader) {
        super(addonLoader, SubServiceStage.UPDATE);
        this.invalidated = new ArrayList<InstalledAddonInfo>();
        this.deleted = new ArrayList<InstalledAddonInfo>();
        this.updates = new HashMap<String, UpdateData>();
    }
    
    @Override
    public void handle() throws Exception {
        final List<InstalledAddonInfo> addons = new ArrayList<InstalledAddonInfo>(this.getAddons());
        if (this.labyModLoader.isAddonDevelopmentEnvironment() || addons.isEmpty()) {
            return;
        }
        this.logger.info("Checking for addon updates...", new Object[0]);
        final JsonObject addonsToProof = new JsonObject();
        for (final InstalledAddonInfo addonInfo : addons) {
            if (addonInfo.getFileHash() == null) {
                this.logger.warn("The addon {} has no file hash. It will not be checked for updates.", addonInfo.getNamespace());
            }
            addonsToProof.addProperty(addonInfo.getNamespace(), addonInfo.getFileHash());
        }
        final Response<JsonObject> webResponse = Request.ofGson(JsonObject.class).url("https://flintmc.net/api/client-store/proof-modification-hashes/%s/%d", new Object[] { FlintUrls.getCurrentReleaseChannel(), BuildData.getBuildNumber() }).method(Request.Method.POST).contentType("application/json").write((Object)addonsToProof.toString()).executeSync();
        if (webResponse.hasException()) {
            this.logger.error("Could not check for addon updates", webResponse.exception());
        }
        if (!webResponse.isPresent()) {
            return;
        }
        final HandledUpdateResponse response = this.handleUpdateResponse(addonsToProof, webResponse.get());
        for (InstalledAddonInfo addonInfo2 : addons) {
            final String namespace = addonInfo2.getNamespace();
            if (!addonInfo2.isFlintAddon()) {
                if (!response.deleted.contains(namespace)) {
                    continue;
                }
                this.deleted.add(addonInfo2);
                this.getAddons().remove(addonInfo2);
                try {
                    Files.delete(addonInfo2.getPath());
                    this.logger.info("The addon {} has been deleted. This is likely due to a critical issue or the addon is no longer supported/maintained.", namespace);
                }
                catch (final Exception e) {
                    this.logError("The addon " + namespace + " was supposed to be deleted. Please delete it manually. Path: " + String.valueOf(addonInfo2.getPath().toAbsolutePath()), (Throwable)e);
                }
            }
            else if (response.invalidated.contains(namespace)) {
                this.invalidated.add(addonInfo2);
                this.getAddons().remove(addonInfo2);
                this.logger.info("The addon {} will not be enabled as it has been invalidated. This is likely due to a critical issue. The addon won't be loaded until the issue has been resolved.", namespace);
            }
            else {
                if (!response.updates.containsKey(namespace)) {
                    continue;
                }
                final UpdateData updateData = response.updates.get(namespace);
                this.logger.info("Updating the addon {} from version {} ({}) to {}...", namespace, updateData.getFromVersion(), updateData.getFromHash(), updateData.getToHash());
                try {
                    final Response<AddonDownloadRequest.AddonDownloadResult> downloadResponse = AddonDownloadRequest.create().loadUniqueIdFromLoader().path(addonInfo2.getDirectoryPath(), addonInfo2.getFileName()).hash(updateData.getToHash()).namespace(addonInfo2.getNamespace()).existsStrategy((expectedNamespace, info) -> true).executeSync();
                    if (downloadResponse.hasException()) {
                        this.logError("Could not download the update of the addon " + namespace, (Throwable)downloadResponse.exception());
                        continue;
                    }
                    if (downloadResponse.isPresent()) {
                        String toVersion = "unknown";
                        final AddonDownloadRequest.AddonDownloadResult result = downloadResponse.get();
                        for (final InstalledAddonInfo info : result.getAddonInfos()) {
                            if (info.getNamespace().equals(namespace)) {
                                toVersion = info.getVersion();
                            }
                            this.addAddon(info, true);
                        }
                        this.logger.info("Successfully updated the addon {} from version {} ({}) to version {} ({}})...", namespace, updateData.getFromVersion(), updateData.getFromHash(), toVersion, updateData.getToHash());
                    }
                }
                catch (final Exception e2) {
                    this.logError("Something went wrong while updating the addon " + namespace, (Throwable)e2);
                }
                this.updates.put(namespace, updateData);
            }
        }
    }
    
    private HandledUpdateResponse handleUpdateResponse(final JsonObject requestObject, final JsonObject updateObject) {
        final HandledUpdateResponse response = new HandledUpdateResponse();
        for (final Map.Entry<String, JsonElement> entry : updateObject.entrySet()) {
            final String namespace = entry.getKey();
            final JsonElement value = entry.getValue();
            if (value.isJsonPrimitive()) {
                if (!value.getAsJsonPrimitive().isString()) {
                    continue;
                }
                final String correctHash = value.getAsString();
                if (correctHash.equals("INVALIDATED")) {
                    response.invalidated.add(namespace);
                }
                else if (correctHash.equals("DELETED")) {
                    response.deleted.add(namespace);
                }
                else {
                    final InstalledAddonInfo addonInfo = this.getAddon(namespace);
                    if (addonInfo == null) {
                        continue;
                    }
                    final String currentHash = requestObject.get(namespace).getAsString();
                    if (correctHash.equals(currentHash)) {
                        continue;
                    }
                    response.updates.put(namespace, new UpdateData(addonInfo.getVersion(), currentHash, correctHash));
                }
            }
        }
        return response;
    }
    
    public class UpdateData
    {
        private final String fromVersion;
        private final String fromHash;
        private final String toHash;
        
        public UpdateData(final AddonUpdater this$0, final String fromVersion, final String fromHash, final String toHash) {
            this.fromVersion = fromVersion;
            this.fromHash = fromHash;
            this.toHash = toHash;
        }
        
        public String getFromVersion() {
            return this.fromVersion;
        }
        
        public String getFromHash() {
            return this.fromHash;
        }
        
        public String getToHash() {
            return this.toHash;
        }
    }
    
    private class HandledUpdateResponse
    {
        private final List<String> invalidated;
        private final List<String> deleted;
        private final Map<String, UpdateData> updates;
        
        public HandledUpdateResponse(final AddonUpdater addonUpdater) {
            this.invalidated = new ArrayList<String>();
            this.deleted = new ArrayList<String>();
            this.updates = new HashMap<String, UpdateData>();
        }
    }
}
