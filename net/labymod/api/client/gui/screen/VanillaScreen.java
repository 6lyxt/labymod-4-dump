// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import java.util.List;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.matrix.Stack;

public interface VanillaScreen
{
    void renderComponentHoverEffect(final Stack p0, final Style p1, final int p2, final int p3);
    
    List<Object> getWrappedWidgets();
}
