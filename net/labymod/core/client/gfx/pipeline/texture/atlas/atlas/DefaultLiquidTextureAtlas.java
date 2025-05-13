// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas.atlas;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gfx.pipeline.texture.atlas.AbstractTextureAtlas;

public class DefaultLiquidTextureAtlas extends AbstractTextureAtlas
{
    public DefaultLiquidTextureAtlas(final ResourceLocation resourceLocation, final String spriteId) {
        super(resourceLocation, 32, 512);
        this.registerAnimatedVertical(this.createLabyMod("block/" + spriteId), 0, 0, 16, 16, 32);
    }
}
