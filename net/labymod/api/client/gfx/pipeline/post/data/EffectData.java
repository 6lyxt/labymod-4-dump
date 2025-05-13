// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;

public class EffectData
{
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private Type type;
    
    public EffectData() {
        this.type = Type.RENDER_TARGET;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        RENDER_TARGET, 
        TEXTURE;
    }
}
