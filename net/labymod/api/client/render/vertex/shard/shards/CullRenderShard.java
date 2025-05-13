// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class CullRenderShard extends BooleanRenderShard
{
    public CullRenderShard(final boolean enabled) {
        super("cull", shard -> {
            if (enabled) {
                shard.gfx().enableCull();
            }
            else {
                shard.gfx().disableCull();
            }
        }, shard -> {
            if (enabled) {
                shard.gfx().disableCull();
            }
            else {
                shard.gfx().enableCull();
            }
        }, enabled);
    }
}
