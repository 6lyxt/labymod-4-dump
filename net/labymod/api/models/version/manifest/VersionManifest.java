// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version.manifest;

import java.util.Map;

public class VersionManifest
{
    public static final String MAPPINGS_DOWNLOAD_KEY = "client_mappings";
    private String id;
    private Map<String, ManifestDownload> downloads;
    
    public VersionManifest(final String id, final Map<String, ManifestDownload> downloads) {
        this.id = id;
        this.downloads = downloads;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public Map<String, ManifestDownload> getDownloads() {
        return this.downloads;
    }
    
    public void setDownloads(final Map<String, ManifestDownload> downloads) {
        this.downloads = downloads;
    }
}
