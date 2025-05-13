// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards.texture;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class NoTexturingRenderShard extends RenderShard
{
    public NoTexturingRenderShard() {
        super("no_texture", shard -> shard.gfx().disableTexture(), shard -> shard.gfx().enableTexture());
    }
}
