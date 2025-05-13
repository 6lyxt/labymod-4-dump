// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards.texture;

import net.labymod.api.client.resources.ResourceLocation;
import java.util.Optional;
import net.labymod.api.client.render.vertex.shard.ShardApplier;
import net.labymod.api.client.render.vertex.shard.RenderShard;

public class EmptyTextureRenderShard extends RenderShard
{
    public EmptyTextureRenderShard(final ShardApplier setupShared, final ShardApplier finishShared) {
        super("texture", setupShared, finishShared);
    }
    
    public EmptyTextureRenderShard() {
        this(shard -> {}, shard -> {});
    }
    
    public Optional<ResourceLocation> texture() {
        return Optional.empty();
    }
}
