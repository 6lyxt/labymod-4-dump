// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.HorizontalAlignment;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ComponentRenderer
{
    ComponentRendererBuilder builder();
    
    ComponentFlattener getFlattener();
    
    ComponentFlattener getColorStrippingFlattener();
    
    void setFlattener(final ComponentFlattener p0);
    
    PlainTextComponentSerializer plainSerializer();
    
    LegacyComponentSerializer legacySectionSerializer();
    
    float width(final Component p0);
    
    float height();
    
    RenderableComponent realignedMerge(final List<RenderableComponent> p0);
    
    ComponentFormatter formatter();
    
    RenderableComponent formatComponent(final Component p0, final float p1, final HorizontalAlignment p2, final float p3, final TextOverflowStrategy p4, final boolean p5, final int p6, final boolean p7, final boolean p8, final boolean p9);
    
    default void renderHoverComponent(final Stack stack, final Mouse mouse, final RenderableComponent component) {
        this.renderHoverComponent(stack, mouse, component, -1, true);
    }
    
    default void renderHoverComponent(final Stack stack, final Mouse mouse, final RenderableComponent component, final int color, final boolean allowColors) {
        this.renderHoverComponent(stack, mouse, component, color, allowColors, Laby.labyAPI().minecraft().minecraftWindow().absoluteBounds());
    }
    
    void renderHoverComponent(final Stack p0, final Mouse p1, final RenderableComponent p2, final int p3, final boolean p4, final Rectangle p5);
    
    void invalidate();
    
    List<Component> split(final Component p0, final float p1);
}
