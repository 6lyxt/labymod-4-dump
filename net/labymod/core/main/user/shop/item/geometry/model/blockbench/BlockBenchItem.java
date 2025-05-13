// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.blockbench;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockBenchItem
{
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("origin")
    @Expose
    public List<Double> origin;
    @SerializedName("rotation")
    @Expose
    public List<Double> rotation;
    @SerializedName("mirror")
    @Expose
    public boolean mirror;
    @SerializedName("locators")
    @Expose
    public Map<String, List<Float>> locators;
    private float absoluteOriginX;
    private float absoluteOriginY;
    private float absoluteOriginZ;
    
    public BlockBenchItem() {
        this.origin = new ArrayList<Double>();
        this.rotation = new ArrayList<Double>();
        this.mirror = false;
        this.locators = new HashMap<String, List<Float>>();
    }
    
    public void setAbsoluteOrigin(final float originX, final float originY, final float originZ) {
        this.absoluteOriginX = originX;
        this.absoluteOriginY = originY;
        this.absoluteOriginZ = originZ;
    }
    
    public float getAbsoluteOriginX() {
        return this.absoluteOriginX;
    }
    
    public float getAbsoluteOriginY() {
        return this.absoluteOriginY;
    }
    
    public float getAbsoluteOriginZ() {
        return this.absoluteOriginZ;
    }
}
