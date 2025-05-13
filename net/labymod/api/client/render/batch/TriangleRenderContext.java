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
public interface TriangleRenderContext extends RenderContext<TriangleRenderContext>
{
    default TriangleRenderContext begin(final Stack stack, final boolean filled) {
        return this.begin(stack, filled, type -> this.getBuilder());
    }
    
    TriangleRenderContext begin(final Stack p0, final boolean p1, final Function<RenderPhase, BufferBuilder> p2);
    
    default TriangleRenderContext begin(final Stack stack, final boolean filled, final float lineWidth) {
        return this.begin(stack, filled, lineWidth, type -> this.getBuilder());
    }
    
    TriangleRenderContext begin(final Stack p0, final boolean p1, final float p2, final Function<RenderPhase, BufferBuilder> p3);
    
    TriangleRenderContext render(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final int p6);
    
    TriangleRenderContext render(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final int p8);
    
    TriangleRenderContext renderLine(final float p0, final float p1, final float p2, final float p3, final int p4);
}
