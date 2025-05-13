// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.WindowWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WindowWidgetRescaleWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final WindowWidget.RescaleWidget rescaleWidget) {
            if (rescaleWidget.render() != null) {
                rescaleWidget.render().reset();
            }
            if (((WindowWidget.RescaleWidget)widget).idleColor() != null) {
                ((WindowWidget.RescaleWidget)widget).idleColor().reset();
            }
            if (((WindowWidget.RescaleWidget)widget).hoverColor() != null) {
                ((WindowWidget.RescaleWidget)widget).hoverColor().reset();
            }
        }
        super.reset(widget);
    }
}
