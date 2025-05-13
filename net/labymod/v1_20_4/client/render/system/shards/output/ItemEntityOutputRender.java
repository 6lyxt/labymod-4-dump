// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.render.system.shards.output;

import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;

@Singleton
@Implements(value = OutputRenderShard.OutputRender.class, key = "item_entity_output_render")
public class ItemEntityOutputRender implements OutputRenderShard.OutputRender
{
    @Override
    public void setup() {
        if (evi.M()) {
            evi.O().f.u().a(true);
        }
    }
    
    @Override
    public void finish() {
        if (evi.M()) {
            evi.O().g().a(false);
        }
    }
}
