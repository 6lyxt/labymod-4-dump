// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray.model;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SprayStorage
{
    @SerializedName("packs")
    private final List<SprayPack> packs;
    
    public SprayStorage() {
        this.packs = new ArrayList<SprayPack>();
    }
    
    public List<SprayPack> getPacks() {
        return this.packs;
    }
}
