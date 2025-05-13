// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;

public interface Renderable
{
    boolean render(final Stack p0, final MutableMouse p1, final float p2);
}
