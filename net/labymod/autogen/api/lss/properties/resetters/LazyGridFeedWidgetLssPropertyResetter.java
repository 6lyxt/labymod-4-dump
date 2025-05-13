// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LazyGridFeedWidgetLssPropertyResetter extends LazyGridWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final LazyGridFeedWidget lazyGridFeedWidget) {
            if (lazyGridFeedWidget.refreshRadius() != null) {
                lazyGridFeedWidget.refreshRadius().reset();
            }
            if (((LazyGridFeedWidget)widget).loadingTextGap() != null) {
                ((LazyGridFeedWidget)widget).loadingTextGap().reset();
            }
            if (((LazyGridFeedWidget)widget).loadingText() != null) {
                ((LazyGridFeedWidget)widget).loadingText().reset();
            }
            if (((LazyGridFeedWidget)widget).loadingColor() != null) {
                ((LazyGridFeedWidget)widget).loadingColor().reset();
            }
            if (((LazyGridFeedWidget)widget).removeLoadingText() != null) {
                ((LazyGridFeedWidget)widget).removeLoadingText().reset();
            }
        }
        super.reset(widget);
    }
}
