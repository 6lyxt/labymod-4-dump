// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HorizontalListWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final HorizontalListWidget horizontalListWidget) {
            if (horizontalListWidget.spaceLeft() != null) {
                horizontalListWidget.spaceLeft().reset();
            }
            if (((HorizontalListWidget)widget).spaceRight() != null) {
                ((HorizontalListWidget)widget).spaceRight().reset();
            }
            if (((HorizontalListWidget)widget).spaceBetweenEntries() != null) {
                ((HorizontalListWidget)widget).spaceBetweenEntries().reset();
            }
            if (((HorizontalListWidget)widget).layout() != null) {
                ((HorizontalListWidget)widget).layout().reset();
            }
        }
        super.reset(widget);
    }
}
