// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostEffectPassData
{
    @SerializedName("name")
    private String name;
    @SerializedName("sourceTarget")
    private String sourceTarget;
    @SerializedName("clearSourceTarget")
    private boolean clearSourceTarget;
    @SerializedName("destinationTarget")
    private String destinationTarget;
    @SerializedName("clearDestinationTarget")
    private boolean clearDestinationTarget;
    @SerializedName("otherTargets")
    private List<EffectData> effects;
    
    public PostEffectPassData() {
        this.clearSourceTarget = false;
        this.clearDestinationTarget = true;
        this.effects = new ArrayList<EffectData>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSourceTarget() {
        return this.sourceTarget;
    }
    
    public boolean isClearSourceTarget() {
        return this.clearSourceTarget;
    }
    
    public String getDestinationTarget() {
        return this.destinationTarget;
    }
    
    public boolean isClearDestinationTarget() {
        return this.clearDestinationTarget;
    }
    
    public List<EffectData> getEffects() {
        return this.effects;
    }
}
