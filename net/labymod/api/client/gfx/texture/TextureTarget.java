// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

public enum TextureTarget
{
    TEXTURE_1D(3552), 
    TEXTURE_1D_ARRAY(35864), 
    TEXTURE_2D(3553), 
    TEXTURE_2D_ARRAY(35866), 
    TEXTURE_3D(32879), 
    TEXTURE_RECTANGLE(34037), 
    TEXTURE_CUBE_MAP(34067), 
    TEXTURE_CUBE_MAP_ARRAY(36873), 
    TEXTURE_BUFFER(35882), 
    TEXTURE_2D_MULTISAMPLE(37120), 
    TEXTURE_2D_MULTISAMPLE_ARRAY(37122);
    
    private final int id;
    
    private TextureTarget(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
