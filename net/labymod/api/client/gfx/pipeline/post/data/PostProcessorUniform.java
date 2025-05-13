// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;

public class PostProcessorUniform
{
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
}
