// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BedrockModel
{
    @SerializedName("format_version")
    @Expose
    public String formatVersion;
    @SerializedName("minecraft:geometry")
    @Expose
    public List<BedrockModelGeometry> bedrockModelGeometry;
    
    public BedrockModel() {
        this.bedrockModelGeometry = null;
    }
}
