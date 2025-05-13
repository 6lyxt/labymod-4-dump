// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox.remote;

import com.google.gson.annotations.SerializedName;

public class Incentive
{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
