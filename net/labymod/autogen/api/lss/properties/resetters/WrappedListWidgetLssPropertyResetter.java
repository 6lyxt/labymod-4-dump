// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WrappedListWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final WrappedListWidget wrappedListWidget) {
            if (wrappedListWidget.spaceBetweenEntries() != null) {
                wrappedListWidget.spaceBetweenEntries().reset();
            }
            if (((WrappedListWidget)widget).verticalSpaceBetweenEntries() != null) {
                ((WrappedListWidget)widget).verticalSpaceBetweenEntries().reset();
            }
            if (((WrappedListWidget)widget).horizontalSpaceBetweenEntries() != null) {
                ((WrappedListWidget)widget).horizontalSpaceBetweenEntries().reset();
            }
            if (((WrappedListWidget)widget).maxLines() != null) {
                ((WrappedListWidget)widget).maxLines().reset();
            }
        }
        super.reset(widget);
    }
}
