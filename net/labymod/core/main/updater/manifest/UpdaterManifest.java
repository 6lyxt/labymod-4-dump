// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.updater.manifest;

import java.util.ArrayList;
import java.util.List;

public final class UpdaterManifest
{
    private String latest;
    private final List<String> versions;
    
    public UpdaterManifest() {
        this.versions = new ArrayList<String>();
    }
    
    public String getLatest() {
        return this.latest;
    }
    
    public List<String> getVersions() {
        return this.versions;
    }
}
