// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class VerticalListWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final VerticalListWidget verticalListWidget) {
            if (verticalListWidget.overwriteWidth() != null) {
                verticalListWidget.overwriteWidth().reset();
            }
            if (((VerticalListWidget)widget).squeezeHeight() != null) {
                ((VerticalListWidget)widget).squeezeHeight().reset();
            }
            if (((VerticalListWidget)widget).selectable() != null) {
                ((VerticalListWidget)widget).selectable().reset();
            }
            if (((VerticalListWidget)widget).spaceBetweenEntries() != null) {
                ((VerticalListWidget)widget).spaceBetweenEntries().reset();
            }
            if (((VerticalListWidget)widget).renderOutOfBounds() != null) {
                ((VerticalListWidget)widget).renderOutOfBounds().reset();
            }
            if (((VerticalListWidget)widget).listAlignment() != null) {
                ((VerticalListWidget)widget).listAlignment().reset();
            }
            if (((VerticalListWidget)widget).listOrder() != null) {
                ((VerticalListWidget)widget).listOrder().reset();
            }
        }
        super.reset(widget);
    }
}
