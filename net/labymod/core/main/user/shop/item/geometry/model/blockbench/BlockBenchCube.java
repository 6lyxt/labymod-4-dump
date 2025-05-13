// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.blockbench;

import net.labymod.core.main.user.shop.item.geometry.model.bedrock.UVData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BlockBenchCube extends BlockBenchItem
{
    @SerializedName("from")
    @Expose
    public List<Double> from;
    @SerializedName("to")
    @Expose
    public List<Double> to;
    @SerializedName("autouv")
    @Expose
    public Integer autouv;
    @SerializedName("color")
    @Expose
    public Integer color;
    @SerializedName("locked")
    @Expose
    public Boolean locked;
    @SerializedName("faces")
    @Expose
    public Faces faces;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("uv_offset")
    @Expose
    public UVData uvOffset;
    @Expose
    public Float inflate;
    
    public BlockBenchCube() {
        this.from = null;
        this.to = null;
        this.uvOffset = new UVData();
        this.inflate = 0.0f;
    }
}
