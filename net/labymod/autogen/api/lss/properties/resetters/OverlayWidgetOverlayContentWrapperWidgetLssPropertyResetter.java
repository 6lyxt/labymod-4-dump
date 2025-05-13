// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class OverlayWidgetOverlayContentWrapperWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof OverlayWidget.OverlayContentWrapperWidget) {}
        super.reset(widget);
    }
}
