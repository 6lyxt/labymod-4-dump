// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

public enum TextureParameterName
{
    TEXTURE_MAG_FILTER(10240), 
    TEXTURE_MIN_FILTER(10241), 
    TEXTURE_WRAP_S(10242), 
    TEXTURE_WRAP_T(10243), 
    TEXTURE_MAX_LEVEL(33085), 
    TEXTURE_MIN_LOD(33082), 
    TEXTURE_MAX_LOD(33083), 
    TEXTURE_LOD_BIAS(34049);
    
    private final int id;
    
    private TextureParameterName(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
