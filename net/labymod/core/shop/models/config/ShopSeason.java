// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.config;

import com.google.gson.annotations.SerializedName;

public class ShopSeason
{
    private String name;
    private String start;
    private String end;
    private boolean preview;
    @SerializedName("nice_name")
    private String niceName;
    @SerializedName("hex_code")
    private String hexCode;
    
    public String getName() {
        return this.name;
    }
    
    public String getStart() {
        return this.start;
    }
    
    public String getEnd() {
        return this.end;
    }
    
    public boolean isPreview() {
        return this.preview;
    }
    
    public String getNiceName() {
        return this.niceName;
    }
    
    public String getHexCode() {
        return this.hexCode;
    }
}
