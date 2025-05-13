// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import net.labymod.api.client.resources.ResourceLocation;

public class PostProcessorConfig
{
    @SerializedName("vertex")
    private ResourceLocation vertex;
    @SerializedName("fragment")
    private ResourceLocation fragment;
    @SerializedName("keywords")
    private final List<String> keywords;
    @SerializedName("targets")
    private List<PostProcessorTarget> targets;
    @SerializedName("passes")
    private List<PostEffectPassData> passes;
    @SerializedName("uniforms")
    private List<PostProcessorUniform> uniforms;
    
    public PostProcessorConfig() {
        this.keywords = new ArrayList<String>();
        this.targets = new ArrayList<PostProcessorTarget>();
        this.passes = new ArrayList<PostEffectPassData>();
        this.uniforms = new ArrayList<PostProcessorUniform>();
    }
    
    public ResourceLocation getVertex() {
        return this.vertex;
    }
    
    public ResourceLocation getFragment() {
        return this.fragment;
    }
    
    public List<String> getKeywords() {
        return this.keywords;
    }
    
    public List<PostProcessorTarget> getTargets() {
        return this.targets;
    }
    
    public List<PostEffectPassData> getPasses() {
        return this.passes;
    }
    
    public List<PostProcessorUniform> getUniforms() {
        return this.uniforms;
    }
}
