// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.window;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;

@Deprecated
@Referenceable
public interface ScreenWindowManagement extends MouseUser, KeyboardUser
{
    void registerOverlay(final AbstractScreenWindowOverlay p0);
    
    void widgetPreInitialize(final Widget p0, final Parent p1);
    
    void renderWidgetOverlay(final Stack p0, final MutableMouse p1, final Widget p2);
    
    void preRenderActivity(final Stack p0, final MutableMouse p1, final Activity p2);
    
     <T extends AbstractScreenWindowOverlay> T overlay(final Class<T> p0);
}
