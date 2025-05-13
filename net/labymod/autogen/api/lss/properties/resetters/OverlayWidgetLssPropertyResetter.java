// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class OverlayWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final OverlayWidget overlayWidget) {
            if (overlayWidget.strategyX() != null) {
                overlayWidget.strategyX().reset();
            }
            if (((OverlayWidget)widget).strategyY() != null) {
                ((OverlayWidget)widget).strategyY().reset();
            }
            if (((OverlayWidget)widget).playInteractSound() != null) {
                ((OverlayWidget)widget).playInteractSound().reset();
            }
        }
        super.reset(widget);
    }
}
