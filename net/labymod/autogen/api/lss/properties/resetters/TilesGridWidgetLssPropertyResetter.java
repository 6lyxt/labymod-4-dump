// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TilesGridWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final TilesGridWidget tilesGridWidget) {
            if (tilesGridWidget.spaceBetweenEntries() != null) {
                tilesGridWidget.spaceBetweenEntries().reset();
            }
            if (((TilesGridWidget)widget).tilesPerLine() != null) {
                ((TilesGridWidget)widget).tilesPerLine().reset();
            }
            if (((TilesGridWidget)widget).tileHeight() != null) {
                ((TilesGridWidget)widget).tileHeight().reset();
            }
        }
        super.reset(widget);
    }
}
