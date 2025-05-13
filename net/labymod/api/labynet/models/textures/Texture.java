// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models.textures;

import java.util.Locale;
import com.google.gson.annotations.SerializedName;

public class Texture
{
    @SerializedName("image_hash")
    private String imageHash;
    private String tags;
    @SerializedName("use_count")
    private int useCount;
    
    public String getImageHash() {
        return this.imageHash;
    }
    
    public String getTags() {
        return this.tags;
    }
    
    public int getUseCount() {
        return this.useCount;
    }
    
    public Texture(final String imageHash, final String tags, final int useCount) {
        this.imageHash = imageHash;
        this.tags = tags;
        this.useCount = useCount;
    }
    
    public Texture() {
    }
    
    public String getDownloadUrl() {
        return String.format(Locale.ROOT, "https://texture.laby.net/%s.png", this.getImageHash());
    }
    
    public String getPreviewUrl() {
        return String.format(Locale.ROOT, "https://skin.laby.net/api/render/texture/%s.png?shadow=true&width=400&height=1024", this.getImageHash());
    }
}
