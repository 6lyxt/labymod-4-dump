// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.service;

public enum ServiceDataType
{
    YOUTUBE("service_youtube", (Class<? extends ServiceData>)YouTubeServiceData.class), 
    TWITCH("service_twitch", (Class<? extends ServiceData>)TwitchServiceData.class), 
    TIKTOK("service_tiktok", (Class<? extends ServiceData>)TikTokServiceData.class);
    
    private final Class<? extends ServiceData> clazz;
    private final String id;
    
    private ServiceDataType(final String id, final Class<? extends ServiceData> clazz) {
        this.id = id;
        this.clazz = clazz;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Class<? extends ServiceData> getClazz() {
        return this.clazz;
    }
}
