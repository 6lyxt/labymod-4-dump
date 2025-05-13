// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version.manifest;

public class ManifestDownload
{
    private String sha1;
    private int size;
    private String url;
    
    public ManifestDownload(final String sha1, final int size, final String url) {
        this.sha1 = sha1;
        this.size = size;
        this.url = url;
    }
    
    public String getSha1() {
        return this.sha1;
    }
    
    public void setSha1(final String sha1) {
        this.sha1 = sha1;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void setSize(final int size) {
        this.size = size;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
