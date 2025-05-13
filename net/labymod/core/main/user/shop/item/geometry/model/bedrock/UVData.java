// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.model.bedrock;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class UVData
{
    private int[] uv;
    private Map<String, Face> faces;
    
    public UVData() {
        this.uv = new int[0];
        this.faces = new HashMap<String, Face>();
    }
    
    public int getUVCoordinate(final int index) {
        if (this.isPerUVFace()) {
            throw new IllegalStateException("Cannot get coordinate");
        }
        return (this.uv.length == 0) ? 0 : this.uv[index];
    }
    
    public void setUv(final int[] uv) {
        this.uv = uv;
    }
    
    public void setFaces(final Map<String, Face> faces) {
        this.faces = faces;
    }
    
    public Map<String, Face> getFaces() {
        return this.faces;
    }
    
    public boolean isPerUVFace() {
        return this.uv.length == 0 && !this.faces.isEmpty();
    }
    
    public static class Face
    {
        @SerializedName("uv")
        private int[] uv;
        @SerializedName("uv_size")
        private int[] sizes;
        
        public int[] getUv() {
            return this.uv;
        }
        
        public int[] getSizes() {
            return this.sizes;
        }
    }
}
