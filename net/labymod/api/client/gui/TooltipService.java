// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TooltipService
{
    void renderTooltip(final Stack p0, final Widget p1, final Mouse p2, final RenderableComponent p3);
    
    void renderFixedTooltip(final Stack p0, final Point p1, final RenderableComponent p2, final boolean p3);
    
    void unhover(final Widget p0);
    
    default void renderFixedTooltip(final Stack stack, final Point point, final RenderableComponent component) {
        this.renderFixedTooltip(stack, point, component, false);
    }
}
