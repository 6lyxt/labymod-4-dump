// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.models.version.Version;

public final class BuildDataModel
{
    private Version latestFullRelease;
    private Version version;
    private int buildNumber;
    private String releaseType;
    private String commitReference;
    private String branchName;
    private String chatTrustFeature;
    
    public Version latestFullRelease() {
        return this.latestFullRelease;
    }
    
    public Version version() {
        return this.version;
    }
    
    public String getReleaseType() {
        return this.releaseType;
    }
    
    public String getCommitReference() {
        return this.commitReference;
    }
    
    public String getBranchName() {
        return this.branchName;
    }
    
    public String getChatTrustFeature() {
        return this.chatTrustFeature;
    }
    
    public int getBuildNumber() {
        return this.buildNumber;
    }
}
