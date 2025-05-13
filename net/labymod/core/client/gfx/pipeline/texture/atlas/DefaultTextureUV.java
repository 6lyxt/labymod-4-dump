// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;

public class DefaultTextureUV implements TextureUV
{
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;
    
    public DefaultTextureUV(final float minU, final float minV, final float maxU, final float maxV) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }
    
    @Override
    public float getMinU() {
        return this.minU;
    }
    
    @Override
    public float getMaxU() {
        return this.maxU;
    }
    
    @Override
    public float getMinV() {
        return this.minV;
    }
    
    @Override
    public float getMaxV() {
        return this.maxV;
    }
}
