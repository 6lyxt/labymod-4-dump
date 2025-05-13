// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class FlexibleContentWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final FlexibleContentWidget flexibleContentWidget) {
            if (flexibleContentWidget.spaceBetweenEntries() != null) {
                flexibleContentWidget.spaceBetweenEntries().reset();
            }
            if (((FlexibleContentWidget)widget).orientation() != null) {
                ((FlexibleContentWidget)widget).orientation().reset();
            }
            if (((FlexibleContentWidget)widget).listOrder() != null) {
                ((FlexibleContentWidget)widget).listOrder().reset();
            }
        }
        super.reset(widget);
    }
}
