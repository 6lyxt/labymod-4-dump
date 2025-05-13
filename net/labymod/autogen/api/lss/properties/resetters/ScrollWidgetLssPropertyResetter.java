// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ScrollWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final ScrollWidget scrollWidget) {
            if (scrollWidget.childAlign() != null) {
                scrollWidget.childAlign().reset();
            }
            if (((ScrollWidget)widget).enableScrollContent() != null) {
                ((ScrollWidget)widget).enableScrollContent().reset();
            }
            if (((ScrollWidget)widget).scrollSpeed() != null) {
                ((ScrollWidget)widget).scrollSpeed().reset();
            }
            if (((ScrollWidget)widget).scrollAlwaysVisible() != null) {
                ((ScrollWidget)widget).scrollAlwaysVisible().reset();
            }
            if (((ScrollWidget)widget).moveDirtBackground() != null) {
                ((ScrollWidget)widget).moveDirtBackground().reset();
            }
            if (((ScrollWidget)widget).fixedPosition() != null) {
                ((ScrollWidget)widget).fixedPosition().reset();
            }
            if (((ScrollWidget)widget).autoScroll() != null) {
                ((ScrollWidget)widget).autoScroll().reset();
            }
            if (((ScrollWidget)widget).modifyContentWidth() != null) {
                ((ScrollWidget)widget).modifyContentWidth().reset();
            }
        }
        super.reset(widget);
    }
}
