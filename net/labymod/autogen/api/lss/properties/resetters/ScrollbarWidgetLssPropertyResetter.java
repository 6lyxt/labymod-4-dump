// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollbarWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ScrollbarWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final ScrollbarWidget scrollbarWidget) {
            if (scrollbarWidget.scrollButtonClickOffset() != null) {
                scrollbarWidget.scrollButtonClickOffset().reset();
            }
            if (((ScrollbarWidget)widget).useLssPosition() != null) {
                ((ScrollbarWidget)widget).useLssPosition().reset();
            }
            if (((ScrollbarWidget)widget).scrollColor() != null) {
                ((ScrollbarWidget)widget).scrollColor().reset();
            }
            if (((ScrollbarWidget)widget).scrollHoverColor() != null) {
                ((ScrollbarWidget)widget).scrollHoverColor().reset();
            }
            if (((ScrollbarWidget)widget).scrollBackgroundColor() != null) {
                ((ScrollbarWidget)widget).scrollBackgroundColor().reset();
            }
            if (((ScrollbarWidget)widget).minScrollHeight() != null) {
                ((ScrollbarWidget)widget).minScrollHeight().reset();
            }
        }
        super.reset(widget);
    }
}
