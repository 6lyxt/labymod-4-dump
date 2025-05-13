// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class LightmapRenderShared extends BooleanRenderShard
{
    public LightmapRenderShared(final boolean enabled) {
        super("lightmap", shard -> {
            if (enabled) {
                shard.gfx().blaze3DGlStatePipeline().enableLightMap();
            }
        }, shard -> {
            if (enabled) {
                shard.gfx().blaze3DGlStatePipeline().disableLightMap();
            }
        }, enabled);
    }
}
