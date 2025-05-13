// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.blockbench;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class East
{
    @SerializedName("uv")
    @Expose
    public List<Integer> uv;
    @SerializedName("texture")
    @Expose
    public Integer texture;
    
    public East() {
        this.uv = null;
    }
}
