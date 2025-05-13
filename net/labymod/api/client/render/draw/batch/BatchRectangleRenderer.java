// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.render.draw.builder.RectangleBuilder;

@Referenceable
public interface BatchRectangleRenderer extends BatchRenderer<BatchRectangleRenderer>, RectangleBuilder<BatchRectangleRenderer>
{
    BatchRectangleRenderer beginBatch(final Stack p0);
    
    BatchRectangleRenderer beginBatch(final Stack p0, final RenderPhase p1);
    
    BatchRectangleRenderer outline(final int p0, final int p1, final float p2);
    
    void zOffset(final float p0);
    
    @Deprecated
    default BatchRectangleRenderer renderOutline(final float left, final float top, final float right, final float bottom, final int outerColor, final int innerColor) {
        return this.pos(left, top, right, bottom).outline(outerColor, innerColor, 1.0f);
    }
    
    @Deprecated
    default BatchRectangleRenderer renderOutline(final float left, final float top, final float right, final float bottom, final int outerColor, final int innerColor, final float thickness) {
        return this.pos(left, top, right, bottom).outline(outerColor, innerColor, thickness);
    }
    
    BatchRectangleRenderer build(final float p0, final float p1, final float p2, final float p3, final int p4);
}
