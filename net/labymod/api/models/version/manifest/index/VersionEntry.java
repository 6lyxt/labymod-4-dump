// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version.manifest.index;

public class VersionEntry
{
    private String id;
    private String type;
    private String url;
    private String time;
    private String releaseTime;
    
    public VersionEntry(final String id, final String type, final String url, final String time, final String releaseTime) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.time = time;
        this.releaseTime = releaseTime;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    public String getReleaseTime() {
        return this.releaseTime;
    }
    
    public void setReleaseTime(final String releaseTime) {
        this.releaseTime = releaseTime;
    }
}
