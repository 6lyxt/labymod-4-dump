// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version.manifest.index;

public class VersionIndex
{
    private LatestVersion latest;
    private VersionEntry[] versions;
    
    public VersionIndex(final LatestVersion latest, final VersionEntry[] versions) {
        this.latest = latest;
        this.versions = versions;
    }
    
    public LatestVersion getLatest() {
        return this.latest;
    }
    
    public void setLatest(final LatestVersion latest) {
        this.latest = latest;
    }
    
    public VersionEntry[] getVersions() {
        return this.versions;
    }
    
    public void setVersions(final VersionEntry[] versions) {
        this.versions = versions;
    }
}
