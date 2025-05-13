// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SprayPack
{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("stickers")
    private List<Spray> sprays;
    
    public SprayPack() {
        this.sprays = new ArrayList<Spray>();
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Spray> getSprays() {
        return this.sprays;
    }
    
    @Override
    public String toString() {
        return "SprayPack[id=" + this.id + ",name=" + this.name + ",sprays=" + String.valueOf(this.sprays);
    }
}
