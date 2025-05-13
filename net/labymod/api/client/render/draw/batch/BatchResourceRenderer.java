// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;

@Referenceable
public interface BatchResourceRenderer extends BatchRenderer<BatchResourceRenderer>, ResourceBuilder<BatchResourceRenderer>
{
    BatchResourceRenderer beginBatch(final Stack p0, final ResourceLocation p1);
}
