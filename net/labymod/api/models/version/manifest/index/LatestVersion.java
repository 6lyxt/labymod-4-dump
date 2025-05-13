// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version.manifest.index;

public class LatestVersion
{
    private String release;
    private String snapshot;
    
    public LatestVersion(final String release, final String snapshot) {
        this.release = release;
        this.snapshot = snapshot;
    }
    
    public String getRelease() {
        return this.release;
    }
    
    public void setRelease(final String release) {
        this.release = release;
    }
    
    public String getSnapshot() {
        return this.snapshot;
    }
    
    public void setSnapshot(final String snapshot) {
        this.snapshot = snapshot;
    }
}
