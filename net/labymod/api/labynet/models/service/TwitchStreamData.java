// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.service;

import com.google.gson.annotations.SerializedName;

public class TwitchStreamData
{
    private String title;
    @SerializedName("game_name")
    private String gameName;
    private String type;
    @SerializedName("viewer_count")
    private int viewerCount;
    
    public String getGameName() {
        return this.gameName;
    }
    
    public String getType() {
        return this.type;
    }
    
    public int getViewerCount() {
        return this.viewerCount;
    }
}
