// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ScreenRendererWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ScreenRendererWidget && ((ScreenRendererWidget)widget).interactable() != null) {
            ((ScreenRendererWidget)widget).interactable().reset();
        }
        super.reset(widget);
    }
}
