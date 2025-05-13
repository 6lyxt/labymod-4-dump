// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards.output;

import net.labymod.api.client.render.vertex.shard.RenderShard;
import javax.inject.Inject;
import javax.inject.Named;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.vertex.shard.shards.OutputRenderShard;

@Singleton
@Implements(OutputRenderShard.class)
public class ItemEntityOutputRenderShard extends OutputRenderShard
{
    @Inject
    public ItemEntityOutputRenderShard(@Named("item_entity_output_render") final OutputRender outputRender) {
        super("item_entity_target", shard -> outputRender.setup(), shard -> outputRender.finish());
    }
}
