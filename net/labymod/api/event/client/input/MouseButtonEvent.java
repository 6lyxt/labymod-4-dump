// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;

public class MouseButtonEvent extends MouseEvent
{
    private final Action action;
    private final MouseButton button;
    
    public MouseButtonEvent(final Action action, final MutableMouse mouse, final MouseButton button) {
        super(mouse);
        this.action = action;
        this.button = button;
    }
    
    public Action action() {
        return this.action;
    }
    
    public MouseButton button() {
        return this.button;
    }
    
    public enum Action
    {
        CLICK, 
        RELEASE;
    }
}
