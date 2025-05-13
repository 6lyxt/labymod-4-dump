// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import com.google.gson.annotations.SerializedName;

public class CosmeticOptionEntry
{
    @SerializedName("custom_key")
    private final String customKey;
    private final String name;
    private final String data;
    private transient String customName;
    
    public CosmeticOptionEntry(final String customKey, final String name, final String data) {
        this.customKey = customKey;
        this.name = name;
        this.data = data;
    }
    
    public String getCustomKey() {
        return this.customKey;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getData() {
        return this.data;
    }
    
    public String getCustomName() {
        return this.customName;
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
    }
    
    @Override
    public String toString() {
        if (this.customName != null) {
            return this.customName;
        }
        if (this.getName() != null) {
            return this.getName();
        }
        return this.getData();
    }
}
