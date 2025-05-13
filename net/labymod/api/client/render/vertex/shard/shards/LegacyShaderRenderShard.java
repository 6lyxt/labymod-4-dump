// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.ShardApplier;
import net.labymod.api.client.render.vertex.shard.RenderShard;

@Deprecated
public class LegacyShaderRenderShard extends RenderShard
{
    public LegacyShaderRenderShard(final ShardApplier setupShared) {
        super("legacy_shader", setupShared, shard -> {});
    }
}
