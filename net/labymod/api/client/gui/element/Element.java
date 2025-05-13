// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.element;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.Interactable;

public interface Element extends Interactable, Parent, MetadataExtension
{
    void postInitialize();
    
    void render(final ScreenContext p0);
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    void render(final Stack p0, final MutableMouse p1, final float p2);
    
    void renderOverlay(final ScreenContext p0);
    
    void renderHoverComponent(final ScreenContext p0);
    
    boolean isVisible();
    
    void setVisible(final boolean p0);
    
    boolean isActive();
    
    boolean isTicking();
    
    void setActive(final boolean p0);
    
    boolean isHovered();
    
    boolean isDragging();
    
    boolean isDraggable();
    
    void setDragging(final boolean p0);
    
    boolean isInteractable();
    
    boolean isInteractableOutside();
}
