// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

public interface CanvasRenderer
{
    void render2D(final Stack p0, final MutableMouse p1, final Canvas p2, final CanvasSide p3, final float p4);
    
    void renderOverlay2D(final Stack p0, final MutableMouse p1, final Canvas p2, final CanvasSide p3, final float p4);
    
    default boolean hasOverlay() {
        return true;
    }
    
    void dispose(final Canvas p0);
    
    public enum CanvasSide
    {
        FRONT, 
        BACK;
    }
}
