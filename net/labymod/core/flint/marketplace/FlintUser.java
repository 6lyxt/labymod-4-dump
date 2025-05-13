// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.marketplace;

import net.labymod.api.client.gui.icon.Icon;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class FlintUser
{
    private UUID uuid;
    @SerializedName("user_name")
    private String userName;
    
    public String getUserName() {
        return this.userName;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public Icon icon() {
        return Icon.head(this.userName);
    }
}
