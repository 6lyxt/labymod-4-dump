// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BedrockModelGeometry
{
    @SerializedName("description")
    @Expose
    public Description description;
    @SerializedName("bones")
    @Expose
    public List<Bone> bones;
    
    public BedrockModelGeometry() {
        this.bones = null;
    }
}
