// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WrappedWidgetLssPropertyResetter extends StyledWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final WrappedWidget wrappedWidget) {
            if (wrappedWidget.priorityLayer() != null) {
                wrappedWidget.priorityLayer().reset();
            }
            if (((WrappedWidget)widget).alignmentX() != null) {
                ((WrappedWidget)widget).alignmentX().reset();
            }
            if (((WrappedWidget)widget).alignmentY() != null) {
                ((WrappedWidget)widget).alignmentY().reset();
            }
            if (((WrappedWidget)widget).renderer() != null) {
                ((WrappedWidget)widget).renderer().reset();
            }
            if (((WrappedWidget)widget).boxSizing() != null) {
                ((WrappedWidget)widget).boxSizing().reset();
            }
        }
        super.reset(widget);
    }
}
