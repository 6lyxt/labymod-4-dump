// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.render.draw.builder.DirectRendererBuilder;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;

@Referenceable
public interface ResourceRenderer extends ResourceBuilder<ResourceRenderer>, DirectRendererBuilder
{
    PlayerHeadRenderer head();
    
    BatchResourceRenderer beginBatch(final Stack p0, final ResourceLocation p1);
    
    HeartRenderer heartRenderer();
    
    EntityHeartRenderer entityHeartRenderer(final LivingEntity p0);
}
