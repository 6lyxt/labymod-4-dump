// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import com.google.gson.annotations.SerializedName;

public class Emote
{
    private int id;
    private String name;
    private int order;
    private boolean rare;
    @SerializedName("item_id")
    private int itemId;
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getOrder() {
        return this.order;
    }
    
    public void setOrder(final int order) {
        this.order = order;
    }
    
    public boolean isRare() {
        return this.rare;
    }
    
    public int getItemId() {
        return this.itemId;
    }
}
