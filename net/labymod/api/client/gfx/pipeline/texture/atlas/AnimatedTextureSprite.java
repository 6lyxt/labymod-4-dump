// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.atlas;

public interface AnimatedTextureSprite extends TextureSprite
{
    default TextureUV uv() {
        return this.uv(0);
    }
    
    TextureUV uv(final int p0);
    
    int getFrames();
}
