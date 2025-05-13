// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridFeedWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TilesGridFeedWidgetLssPropertyResetter extends TilesGridWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TilesGridFeedWidget && ((TilesGridFeedWidget)widget).refreshRadius() != null) {
            ((TilesGridFeedWidget)widget).refreshRadius().reset();
        }
        super.reset(widget);
    }
}
