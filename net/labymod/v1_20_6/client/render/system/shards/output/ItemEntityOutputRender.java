// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.render.system.shards.output;

import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;

@Singleton
@Implements(value = OutputRenderShard.OutputRender.class, key = "item_entity_output_render")
public class ItemEntityOutputRender implements OutputRenderShard.OutputRender
{
    @Override
    public void setup() {
        if (ffh.O()) {
            ffh.Q().f.u().a(true);
        }
    }
    
    @Override
    public void finish() {
        if (ffh.O()) {
            ffh.Q().h().a(false);
        }
    }
}
