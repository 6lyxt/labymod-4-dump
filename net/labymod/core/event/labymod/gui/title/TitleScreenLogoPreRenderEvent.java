// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.labymod.gui.title;

import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.LogoWidgetAccessor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;

public class TitleScreenLogoPreRenderEvent implements Event
{
    private final Stack stack;
    private final MutableMouse mouse;
    private final float partialTicks;
    private final LogoWidgetAccessor logo;
    
    public TitleScreenLogoPreRenderEvent(final Stack stack, final MutableMouse mouse, final float partialTicks, final LogoWidgetAccessor logo) {
        this.stack = stack;
        this.mouse = mouse;
        this.partialTicks = partialTicks;
        this.logo = logo;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public LogoWidgetAccessor logo() {
        return this.logo;
    }
}
