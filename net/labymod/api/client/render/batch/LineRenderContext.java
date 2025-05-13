// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.batch;

import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LineRenderContext extends RenderContext<LineRenderContext>
{
    default LineRenderContext begin(final Stack stack) {
        return this.begin(stack, type -> this.getBuilder());
    }
    
    LineRenderContext begin(final Stack p0, final Function<RenderPhase, BufferBuilder> p1);
    
    LineRenderContext renderGradient(final float p0, final float p1, final float p2, final float p3, final int p4, final int p5);
    
    LineRenderContext render(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
}
