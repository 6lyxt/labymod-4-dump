// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import java.util.UUID;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BedrockCube
{
    @SerializedName("origin")
    @Expose
    public List<Double> origin;
    @SerializedName("size")
    @Expose
    public List<Double> size;
    @SerializedName("uv")
    @Expose
    public UVData uv;
    @SerializedName("pivot")
    @Expose
    public List<Double> pivot;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    @SerializedName("mirror")
    @Expose
    public Boolean mirror;
    @Expose
    public Float inflate;
    public String uuid;
    
    public BedrockCube() {
        this.origin = null;
        this.size = null;
        this.uv = new UVData();
        this.pivot = null;
        this.rotation = null;
        this.mirror = false;
        this.inflate = 0.0f;
        this.uuid = UUID.randomUUID().toString();
    }
}
