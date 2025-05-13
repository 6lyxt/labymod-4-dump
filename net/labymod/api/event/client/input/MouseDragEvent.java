// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;

public class MouseDragEvent extends MouseEvent
{
    private final MouseButton button;
    private final double deltaX;
    private final double deltaY;
    
    public MouseDragEvent(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        super(mouse);
        this.button = button;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    
    public MouseButton button() {
        return this.button;
    }
    
    public double deltaX() {
        return this.deltaX;
    }
    
    public double deltaY() {
        return this.deltaY;
    }
}
