// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class LineWidthRenderShard extends RenderShard
{
    private final float lineWidth;
    
    public LineWidthRenderShard(final float lineWidth) {
        super("line_width", shard -> shard.gfx().blaze3DGlStatePipeline().setLineWidth(lineWidth), shard -> shard.gfx().blaze3DGlStatePipeline().setLineWidth(1.0f));
        this.lineWidth = lineWidth;
    }
    
    public float getLineWidth() {
        return this.lineWidth;
    }
}
