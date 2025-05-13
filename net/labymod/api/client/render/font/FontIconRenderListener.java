// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;

public interface FontIconRenderListener
{
    public static final FontIconRenderListener NORMAL_2D = FontIconRenderListeners.NORMAL_2D;
    public static final FontIconRenderListener NORMAL_3D = FontIconRenderListeners.NORMAL_3D;
    
    default void preRender() {
    }
    
    default void render(final Stack stack, final Icon icon, final float x, final float y, final float width, final float height, final int rgb) {
        icon.render(stack, x, y, width, height, false, rgb);
    }
    
    default void postRender() {
    }
}
