// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface WidgetRenderer<T extends Widget>
{
    default void render(final T widget, final ScreenContext context) {
        this.renderPre(widget, context);
        this.renderPost(widget, context);
    }
    
    default void renderWidget(final T widget, final ScreenContext context) {
        widget.renderWidget(context);
    }
    
    void renderPre(final T p0, final ScreenContext p1);
    
    void renderPost(final T p0, final ScreenContext p1);
    
    public enum RenderPhase
    {
        PRE, 
        POST;
    }
}
