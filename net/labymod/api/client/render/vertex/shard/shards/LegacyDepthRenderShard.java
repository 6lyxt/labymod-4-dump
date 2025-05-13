// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class LegacyDepthRenderShard extends RenderShard
{
    public LegacyDepthRenderShard(final boolean enabled) {
        super("legacy_depth", shard -> {
            if (enabled) {
                shard.gfx().enableDepth();
            }
            else {
                shard.gfx().disableDepth();
            }
            shard.gfx().depthMask(enabled);
        }, shard -> {
            if (enabled) {
                shard.gfx().disableDepth();
            }
            else {
                shard.gfx().enableDepth();
            }
            shard.gfx().depthMask(!enabled);
        });
    }
}
