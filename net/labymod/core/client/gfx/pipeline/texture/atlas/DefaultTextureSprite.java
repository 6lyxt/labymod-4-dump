// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;

public class DefaultTextureSprite implements TextureSprite
{
    private final TextureUV uv;
    protected final float width;
    protected final float height;
    protected final SpriteScaling scaling;
    
    public DefaultTextureSprite(final float minU, final float minV, final float maxU, final float maxV, final SpriteScaling.Factory spriteScalingFactory) {
        this(minU, minV, maxU, maxV, (maxU - minU) * 256.0f, (maxV - minV) * 256.0f, spriteScalingFactory);
    }
    
    public DefaultTextureSprite(final float minU, final float minV, final float maxU, final float maxV, final float width, final float height, final SpriteScaling.Factory spriteScalingFactory) {
        this(new DefaultTextureUV(minU, minV, maxU, maxV), width, height, spriteScalingFactory);
    }
    
    public DefaultTextureSprite(final TextureUV uv, final float width, final float height, final SpriteScaling.Factory spriteScalingFactory) {
        this.uv = uv;
        this.width = width;
        this.height = height;
        this.scaling = spriteScalingFactory.create(width, height);
    }
    
    @Override
    public TextureUV uv() {
        return this.uv;
    }
    
    @Override
    public SpriteScaling scaling() {
        return this.scaling;
    }
}
