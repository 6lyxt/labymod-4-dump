// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.atlas;

public interface TextureUV
{
    float getMinU();
    
    float getMaxU();
    
    float getMinV();
    
    float getMaxV();
    
    default float getU(final float value) {
        final float diff = this.getMaxU() - this.getMinU();
        return this.getMinU() + diff * value;
    }
    
    default float getV(final float value) {
        final float diff = this.getMaxV() - this.getMinV();
        return this.getMinV() + diff * value;
    }
}
