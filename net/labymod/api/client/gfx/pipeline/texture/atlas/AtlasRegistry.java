// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.atlas;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AtlasRegistry
{
    default void register(final TextureAtlas atlas) {
        this.register(atlas.resource(), atlas);
    }
    
    void register(final ResourceLocation p0, final TextureAtlas p1);
    
    @NotNull
    TextureAtlas getAtlas(final ResourceLocation p0);
}
