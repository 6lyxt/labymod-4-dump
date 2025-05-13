// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import net.labymod.api.client.render.vertex.shard.RenderShard;

public class WriteMaskRenderShard extends RenderShard
{
    private final boolean writeColor;
    private final boolean writeDepth;
    
    public WriteMaskRenderShard(final boolean writeColor, final boolean writeDepth) {
        super("write_mask_state", shard -> {
            if (!writeDepth) {
                shard.gfx().depthMask(writeDepth);
            }
            if (!writeColor) {
                shard.gfx().colorMask(writeColor, writeColor, writeColor, writeColor);
            }
            return;
        }, shard -> {
            if (!writeDepth) {
                shard.gfx().depthMask(true);
            }
            if (!writeColor) {
                shard.gfx().colorMask(true, true, true, true);
            }
            return;
        });
        this.writeColor = writeColor;
        this.writeDepth = writeDepth;
    }
}
