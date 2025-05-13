// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public final class WidgetUtil
{
    private WidgetUtil() {
    }
    
    public static Widget unwrapWidget(Widget widget) {
        while (widget instanceof WrappedWidget) {
            final WrappedWidget wrappedWidget = (WrappedWidget)widget;
            widget = wrappedWidget.childWidget();
        }
        return widget;
    }
}
