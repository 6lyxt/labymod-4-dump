// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.system.shards.output;

import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;

@Singleton
@Implements(value = OutputRenderShard.OutputRender.class, key = "item_entity_output_render")
public class ItemEntityOutputRender implements OutputRenderShard.OutputRender
{
    @Override
    public void setup() {
        if (djz.A()) {
            djz.C().e.r().a(false);
        }
    }
    
    @Override
    public void finish() {
        if (djz.A()) {
            djz.C().f().a(false);
        }
    }
}
