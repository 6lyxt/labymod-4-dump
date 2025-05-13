// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class AlphaRenderShard extends RenderShard
{
    public AlphaRenderShard(final String name) {
        super(name, shard -> shard.gfx().blaze3DGlStatePipeline().alphaFunc(516, 0.003921569f), shard -> shard.gfx().blaze3DGlStatePipeline().defaultAlphaFunc());
    }
}
