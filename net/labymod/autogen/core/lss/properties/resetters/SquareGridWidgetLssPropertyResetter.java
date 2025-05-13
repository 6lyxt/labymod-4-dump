// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SquareGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SquareGridWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final SquareGridWidget squareGridWidget) {
            if (squareGridWidget.preferredSquareHeight() != null) {
                squareGridWidget.preferredSquareHeight().reset();
            }
            if (((SquareGridWidget)widget).spaceBetweenEntries() != null) {
                ((SquareGridWidget)widget).spaceBetweenEntries().reset();
            }
        }
        super.reset(widget);
    }
}
