// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.playerlist;

import net.labymod.api.event.Event;

public class ServerBannerEvent implements Event
{
    private String url;
    private String hash;
    
    public ServerBannerEvent(final String url, final String hash) {
        this.url = url;
        this.hash = hash;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public String getHash() {
        return this.hash;
    }
    
    public void setHash(final String hash) {
        this.hash = hash;
    }
}
