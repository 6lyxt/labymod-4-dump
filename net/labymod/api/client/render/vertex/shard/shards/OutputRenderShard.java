// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.ShardApplier;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.render.vertex.shard.RenderShard;

@Referenceable
public abstract class OutputRenderShard extends RenderShard
{
    public OutputRenderShard(final String name, final ShardApplier setupShard, final ShardApplier finishShard) {
        super(name, setupShard, finishShard);
    }
    
    @Referenceable(named = true)
    public interface OutputRender
    {
        void setup();
        
        void finish();
    }
}
