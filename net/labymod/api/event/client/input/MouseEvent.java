// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public abstract class MouseEvent extends DefaultCancellable implements Event
{
    private final MutableMouse mouse;
    
    public MouseEvent(final MutableMouse mouse) {
        this.mouse = mouse;
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
}
