// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.model;

import com.google.gson.annotations.SerializedName;

public class DailyEmoteFlat
{
    @SerializedName("e")
    private boolean hasFlat;
    
    public boolean hasFlat() {
        return this.hasFlat;
    }
    
    public void setHasFlat(final boolean hasFlat) {
        this.hasFlat = hasFlat;
    }
    
    public DailyEmoteFlat copy() {
        final DailyEmoteFlat copy = new DailyEmoteFlat();
        copy.hasFlat = this.hasFlat;
        return copy;
    }
}
