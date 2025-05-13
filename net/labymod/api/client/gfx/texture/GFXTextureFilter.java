// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.texture;

import net.labymod.api.client.gfx.GFXObject;

@Deprecated(forRemoval = true, since = "4.2.41")
public enum GFXTextureFilter implements GFXObject
{
    NEAREST(TextureFilter.NEAREST), 
    LINEAR(TextureFilter.LINEAR), 
    NEAREST_MIPMAP_NEAREST(TextureFilter.NEAREST_MIPMAP_NEAREST), 
    LINEAR_MIPMAP_NEAREST(TextureFilter.LINEAR_MIPMAP_NEAREST), 
    NEAREST_MIPMAP_LINEAR(TextureFilter.NEAREST_MIPMAP_LINEAR), 
    LINEAR_MIPMAP_LINEAR(TextureFilter.LINEAR_MIPMAP_LINEAR);
    
    private final TextureFilter filter;
    
    private GFXTextureFilter(final TextureFilter filter) {
        this.filter = filter;
    }
    
    @Override
    public int getHandle() {
        return this.filter.getId();
    }
}
