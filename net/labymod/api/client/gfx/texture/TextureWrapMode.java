// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

public enum TextureWrapMode
{
    CLAMP(10496), 
    REPEAT(10497), 
    CLAMP_TO_EDGE(33071), 
    MIRRORED_REPEAT(33648);
    
    private final int id;
    
    private TextureWrapMode(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
