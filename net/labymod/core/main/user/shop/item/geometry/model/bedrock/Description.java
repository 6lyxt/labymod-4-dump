// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Description
{
    @SerializedName("identifier")
    @Expose
    public String identifier;
    @SerializedName("texture_width")
    @Expose
    public Integer textureWidth;
    @SerializedName("texture_height")
    @Expose
    public Integer textureHeight;
    @SerializedName("visible_bounds_width")
    @Expose
    public Integer visibleBoundsWidth;
    @SerializedName("visible_bounds_height")
    @Expose
    public Double visibleBoundsHeight;
    @SerializedName("visible_bounds_offset")
    @Expose
    public List<Double> visibleBoundsOffset;
    
    public Description() {
        this.visibleBoundsOffset = null;
    }
}
