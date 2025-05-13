// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards.layer;

import net.labymod.api.client.render.vertex.shard.RenderShard;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.shard.shards.LayeringRenderShard;

public class ViewOffsetZLayeringRenderShard extends LayeringRenderShard
{
    private static ViewOffsetZLayer viewOffsetZLayer;
    
    public ViewOffsetZLayeringRenderShard() {
        super("view_offset_z_layering", shard -> getViewOffsetZLayer().setup(), shard -> getViewOffsetZLayer().finish());
    }
    
    public static ViewOffsetZLayer getViewOffsetZLayer() {
        if (ViewOffsetZLayeringRenderShard.viewOffsetZLayer == null) {
            ViewOffsetZLayeringRenderShard.viewOffsetZLayer = Laby.references().viewOffsetZLayer();
        }
        return ViewOffsetZLayeringRenderShard.viewOffsetZLayer;
    }
}
