// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.api.client.render.gl.GlStateBridge;

public abstract class RenderShard
{
    private final String name;
    private final ShardApplier setupShared;
    private final ShardApplier finishShared;
    
    public RenderShard(final String name, final ShardApplier setupShard, final ShardApplier finishShard) {
        this.name = name;
        this.setupShared = setupShard;
        this.finishShared = finishShard;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setupShared() {
        this.setupShared.apply(this);
    }
    
    public void finishShared() {
        this.finishShared.apply(this);
    }
    
    @Deprecated
    public GlStateBridge bridge() {
        return Laby.labyAPI().renderPipeline().glStateBridge();
    }
    
    public GFXBridge gfx() {
        return Laby.labyAPI().gfxRenderPipeline().gfx();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
