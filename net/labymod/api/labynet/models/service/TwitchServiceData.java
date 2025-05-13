// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.service;

import org.jetbrains.annotations.Nullable;
import com.google.gson.annotations.SerializedName;

public class TwitchServiceData extends ServiceData
{
    @SerializedName("login")
    private String userName;
    @SerializedName("display_name")
    private String displayName;
    @Nullable
    private TwitchStreamData stream;
    
    public String getUserName() {
        return this.userName;
    }
    
    @Nullable
    public TwitchStreamData getStream() {
        return this.stream;
    }
}
