// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.atlas;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;

public interface TextureAtlas
{
    void register(final ResourceLocation p0, final TextureSprite p1);
    
    @Deprecated(forRemoval = true, since = "4.0.6")
    @Nullable
    default TextureSprite getSprite(final ResourceLocation location) {
        return this.findSprite(location);
    }
    
    TextureSprite findSprite(final ResourceLocation p0);
    
    ResourceLocation resource();
    
    int getAtlasWidth();
    
    int getAtlasHeight();
}
