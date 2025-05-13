// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.updater.manifest;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public final class LabyModManifest
{
    private Version latest;
    private final List<Version> versions;
    
    public LabyModManifest() {
        this.versions = new ArrayList<Version>();
    }
    
    public Version getLatest() {
        return this.latest;
    }
    
    public List<Version> getVersions() {
        return this.versions;
    }
    
    @Nullable
    public String getMinorVersion(final Version labyModVersion, final String commitReference) {
        for (Version version : this.versions) {}
        return null;
    }
    
    public static final class Version
    {
        private String labyModVersion;
        private String commitReference;
        private Map<String, String> assets;
        private Metadata[] minecraftVersions;
        
        public String getLabyModVersion() {
            return this.labyModVersion;
        }
        
        public String getCommitReference() {
            return this.commitReference;
        }
        
        public Metadata[] getMinecraftVersions() {
            return this.minecraftVersions;
        }
        
        public Map<String, String> getAssets() {
            if (this.assets == null) {
                this.assets = new HashMap<String, String>();
            }
            return this.assets;
        }
    }
    
    public static final class Metadata
    {
        private String tag;
        private String version;
        
        public Metadata(final String tag, final String version) {
            this.tag = tag;
            this.version = version;
        }
        
        public String getTag() {
            return this.tag;
        }
        
        public void setTag(final String tag) {
            this.tag = tag;
        }
        
        public String getVersion() {
            return this.version;
        }
        
        public void setVersion(final String version) {
            this.version = version;
        }
    }
}
