// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture;

import net.labymod.api.client.resources.CompletableResourceLocation;

public class RemoteTexture
{
    private final String url;
    private final CompletableResourceLocation location;
    private boolean downloadStarted;
    
    public RemoteTexture(final String url, final CompletableResourceLocation location) {
        this.url = url;
        this.location = location;
    }
    
    public String url() {
        return this.url;
    }
    
    public CompletableResourceLocation location() {
        return this.location;
    }
    
    public boolean wasDownloadStarted() {
        return this.downloadStarted;
    }
    
    public void setDownloadStarted(final boolean downloadStarted) {
        this.downloadStarted = downloadStarted;
    }
}
