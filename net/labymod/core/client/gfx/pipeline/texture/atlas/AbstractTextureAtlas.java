// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import java.util.HashMap;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;

public class AbstractTextureAtlas implements TextureAtlas
{
    protected final Map<ResourceLocation, TextureSprite> sprites;
    private final ResourceLocation resourceLocation;
    private final int width;
    private final int height;
    
    public AbstractTextureAtlas(final ResourceLocation resourceLocation, final int width, final int height) {
        this.sprites = new HashMap<ResourceLocation, TextureSprite>();
        this.resourceLocation = resourceLocation;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void register(final ResourceLocation id, final TextureSprite sprite) {
        this.sprites.put(id, sprite);
    }
    
    @Override
    public TextureSprite findSprite(final ResourceLocation location) {
        return this.sprites.get(location);
    }
    
    @Override
    public ResourceLocation resource() {
        return this.resourceLocation;
    }
    
    @Override
    public int getAtlasWidth() {
        return this.width;
    }
    
    @Override
    public int getAtlasHeight() {
        return this.height;
    }
    
    protected void register16(final ResourceLocation id, final int x, final int y) {
        this.registerTile(id, x, y, 16);
    }
    
    protected void register8(final ResourceLocation id, final int x, final int y) {
        this.registerTile(id, x, y, 8);
    }
    
    protected void registerTile(final ResourceLocation id, final int x, final int y, final int size) {
        this.registerTile(id, x, y, size, size);
    }
    
    protected void registerTile(final ResourceLocation id, final int x, final int y, final int width, final int height) {
        this.register(id, x * width, y * height, width, height, (width1, height1) -> new TileScaling((int)width1, (int)height1));
    }
    
    protected void register(final ResourceLocation id, final int x, final int y, final int width, final int height, final SpriteScaling.Factory spriteScalingFactory) {
        final TextureUV uv = this.createUV(x, y, width, height);
        this.register(id, new DefaultTextureSprite(uv, (float)width, (float)height, spriteScalingFactory));
    }
    
    public void registerAnimatedHorizontal(final ResourceLocation id, final int x, final int y, final int width, final int height, final int frames) {
        this.registerAnimated(id, x, y, width, height, frames, true);
    }
    
    public void registerAnimatedVertical(final ResourceLocation id, final int x, final int y, final int width, final int height, final int frames) {
        this.registerAnimated(id, x, y, width, height, frames, false);
    }
    
    public void registerAnimated(final ResourceLocation id, int x, int y, final int width, final int height, final int frames, final boolean horizontal) {
        final Int2ObjectMap<TextureSprite> sprites = (Int2ObjectMap<TextureSprite>)new Int2ObjectArrayMap(frames);
        for (int index = 0; index < frames; ++index) {
            if (horizontal) {
                x += ((index == 0) ? 0 : width);
            }
            else {
                y += ((index == 0) ? 0 : height);
            }
            final TextureUV uv = this.createUV(x, y, width, height);
            sprites.put(index, (Object)new DefaultTextureSprite(uv, (float)width, (float)height, (w, h) -> new TileScaling((int)w, (int)h)));
        }
        this.register(id, new DefaultAnimatedTextureSprite(sprites));
    }
    
    protected ResourceLocation createMinecraft(final String path) {
        return ResourceLocation.create("minecraft", path);
    }
    
    protected ResourceLocation createLabyMod(final String path) {
        return ResourceLocation.create("labymod", path);
    }
    
    private TextureUV createUV(final int x, final int y, final int width, final int height) {
        final float minU = x / (float)this.width;
        final float minV = y / (float)this.height;
        final float maxU = (x + width) / (float)this.width;
        final float maxV = (y + height) / (float)this.height;
        return new DefaultTextureUV(minU, minV, maxU, maxV);
    }
}
