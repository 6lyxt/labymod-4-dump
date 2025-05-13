// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;

public class MouseScrollEvent extends MouseEvent
{
    private final float delta;
    
    public MouseScrollEvent(final MutableMouse mouse, final float delta) {
        super(mouse);
        this.delta = delta;
    }
    
    public float delta() {
        return this.delta;
    }
}
