// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class SmoothLineRenderShard extends RenderShard
{
    public SmoothLineRenderShard() {
        super("smooth_line", shard -> shard.gfx().linesSmooth(), shard -> {});
    }
}
