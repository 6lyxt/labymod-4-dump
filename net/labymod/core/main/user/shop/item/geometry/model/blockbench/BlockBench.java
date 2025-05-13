// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.blockbench;

import java.util.ArrayList;
import com.google.gson.JsonArray;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockBench
{
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("geometry_name")
    @Expose
    public String geometryName;
    @SerializedName("modded_entity_version")
    @Expose
    public String moddedEntityVersion;
    @SerializedName("visible_box")
    @Expose
    public List<Double> visibleBox;
    @SerializedName("layered_textures")
    @Expose
    public Boolean layeredTextures;
    @SerializedName("resolution")
    @Expose
    public Resolution resolution;
    @SerializedName("elements")
    @Expose
    public List<BlockBenchCube> elements;
    @SerializedName("outliner")
    @Expose
    public JsonArray outliner;
    @SerializedName("textures")
    @Expose
    public List<Texture> textures;
    
    public BlockBench() {
        this.visibleBox = null;
        this.resolution = new Resolution();
        this.elements = new ArrayList<BlockBenchCube>();
        this.outliner = new JsonArray();
        this.textures = null;
    }
}
