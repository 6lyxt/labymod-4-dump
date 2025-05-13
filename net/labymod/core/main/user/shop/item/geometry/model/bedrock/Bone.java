// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bone
{
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("pivot")
    @Expose
    public List<Double> pivot;
    @SerializedName("parent")
    @Expose
    public String parent;
    @SerializedName("cubes")
    @Expose
    public List<BedrockCube> cubes;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    @SerializedName("locators")
    @Expose
    public Map<String, List<Float>> locators;
    public String uuid;
    
    public Bone() {
        this.pivot = null;
        this.cubes = new ArrayList<BedrockCube>();
        this.rotation = new ArrayList<Double>();
        this.locators = new HashMap<String, List<Float>>();
        this.uuid = UUID.randomUUID().toString();
    }
}
