// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface VanillaWindowBuilder
{
    VanillaWindowBuilder top(final boolean p0);
    
    VanillaWindowBuilder bottom(final boolean p0);
    
    VanillaWindowBuilder rescalable(final boolean p0);
    
    VanillaWindowBuilder position(final Rectangle p0);
    
    VanillaWindowBuilder render(final Stack p0, final MutableMouse p1);
}
