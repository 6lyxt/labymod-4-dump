// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;

@Singleton
@Implements(MinecraftAtlases.class)
public class VersionedMinecraftAtlases implements MinecraftAtlases
{
    @Inject
    public VersionedMinecraftAtlases() {
    }
    
    @Override
    public TextureAtlas getGuiAtlas() {
        final TextureAtlasHolderAccessor accessor = (TextureAtlasHolderAccessor)ffg.Q().aH();
        return (TextureAtlas)accessor.getTextureAtlas();
    }
}
