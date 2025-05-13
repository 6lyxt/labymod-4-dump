// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Event;

public class ScreenUpdateVanillaWidgetEvent implements Event
{
    private final AbstractWidget<?> widget;
    
    public ScreenUpdateVanillaWidgetEvent(final AbstractWidget<?> widget) {
        this.widget = widget;
    }
    
    public AbstractWidget<?> getWidget() {
        return this.widget;
    }
}
