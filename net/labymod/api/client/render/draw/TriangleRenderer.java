// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TriangleRenderer
{
    void render(@NotNull final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final int p7, final boolean p8);
    
    void renderUnderlined(@NotNull final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final int p7, final int p8, final float p9);
    
    void renderBordered(@NotNull final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final int p7, final int p8, final float p9);
    
    void renderTrapezoid(@NotNull final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final int p9, final boolean p10);
    
    void renderTrapezoidBordered(@NotNull final Stack p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final int p9, final int p10, final float p11);
}
