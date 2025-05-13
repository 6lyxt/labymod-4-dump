// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import com.google.gson.annotations.SerializedName;

public class GameUserItemData
{
    @SerializedName("i")
    private int identifier;
    @SerializedName("d")
    private String[] options;
    
    public GameUserItemData() {
    }
    
    public GameUserItemData(final int identifier, final String[] options) {
        this.identifier = identifier;
        this.options = options;
    }
    
    public int getIdentifier() {
        return this.identifier;
    }
    
    public String[] getOptions() {
        return this.options;
    }
}
