// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;

public interface VersionedMinecraft
{
    boolean dispatchKeyboardInput(final LabyScreenRenderer p0);
    
    boolean dispatchMouseInput();
    
    boolean handleMouseDragged(final MutableMouse p0, final MouseButton p1, final double p2, final double p3);
    
    MouseButton getCurrentEventButton();
}
