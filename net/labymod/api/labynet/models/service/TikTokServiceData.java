// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.service;

import com.google.gson.annotations.SerializedName;

public class TikTokServiceData extends ServiceData
{
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("follower_count")
    private int followerCount;
    @SerializedName("likes_count")
    private int likesCount;
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public int getFollowerCount() {
        return this.followerCount;
    }
    
    public int getLikesCount() {
        return this.likesCount;
    }
}
