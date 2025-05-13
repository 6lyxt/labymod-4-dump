// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.navigation.NavigationListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class NavigationListWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final NavigationListWidget navigationListWidget) {
            if (navigationListWidget.priorityAlignment() != null) {
                navigationListWidget.priorityAlignment().reset();
            }
            if (((NavigationListWidget)widget).maxPadding() != null) {
                ((NavigationListWidget)widget).maxPadding().reset();
            }
        }
        super.reset(widget);
    }
}
