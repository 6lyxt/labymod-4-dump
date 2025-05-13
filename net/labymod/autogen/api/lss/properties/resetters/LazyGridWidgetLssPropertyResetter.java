// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LazyGridWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final LazyGridWidget lazyGridWidget) {
            if (lazyGridWidget.tilesPerLine() != null) {
                lazyGridWidget.tilesPerLine().reset();
            }
            if (((LazyGridWidget)widget).dynamicTilesPerLine() != null) {
                ((LazyGridWidget)widget).dynamicTilesPerLine().reset();
            }
            if (((LazyGridWidget)widget).tileHeight() != null) {
                ((LazyGridWidget)widget).tileHeight().reset();
            }
            if (((LazyGridWidget)widget).tileWidth() != null) {
                ((LazyGridWidget)widget).tileWidth().reset();
            }
            if (((LazyGridWidget)widget).tileSize() != null) {
                ((LazyGridWidget)widget).tileSize().reset();
            }
            if (((LazyGridWidget)widget).spaceBetweenEntries() != null) {
                ((LazyGridWidget)widget).spaceBetweenEntries().reset();
            }
        }
        super.reset(widget);
    }
}
