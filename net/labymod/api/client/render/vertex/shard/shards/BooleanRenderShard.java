// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.ShardApplier;
import net.labymod.api.client.render.vertex.shard.RenderShard;

public abstract class BooleanRenderShard extends RenderShard
{
    private final boolean enabled;
    
    public BooleanRenderShard(final String name, final ShardApplier setupShared, final ShardApplier finishShared, final boolean enabled) {
        super(name, setupShared, finishShared);
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        return this.getName() + "[" + this.enabled;
    }
}
