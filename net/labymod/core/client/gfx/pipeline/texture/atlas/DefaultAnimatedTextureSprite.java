// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;

public class DefaultAnimatedTextureSprite implements AnimatedTextureSprite
{
    private final Int2ObjectMap<TextureSprite> sprites;
    private final int frames;
    
    public DefaultAnimatedTextureSprite(final Int2ObjectMap<TextureSprite> sprites) {
        this.sprites = sprites;
        this.frames = sprites.size();
    }
    
    @Override
    public TextureUV uv(final int frame) {
        final TextureSprite sprite = (TextureSprite)this.sprites.get(frame);
        return (sprite == null) ? null : sprite.uv();
    }
    
    @Override
    public int getFrames() {
        return this.frames;
    }
    
    @Override
    public SpriteScaling scaling() {
        return null;
    }
}
