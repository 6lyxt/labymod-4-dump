// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class DepthTestRenderShard extends RenderShard
{
    private final String functionName;
    
    public DepthTestRenderShard(final String functionName, final int func) {
        super("depth_test", shard -> {
            if (func != 519) {
                shard.gfx().enableDepth();
                shard.gfx().depthFunc(func);
            }
            return;
        }, shard -> {
            if (func != 519) {
                shard.gfx().disableDepth();
                shard.gfx().depthFunc(519);
            }
            return;
        });
        this.functionName = functionName;
    }
    
    @Override
    public String toString() {
        return this.getName() + "[" + this.functionName;
    }
}
