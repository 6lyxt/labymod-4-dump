// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HorizontalListEntryLssPropertyResetter extends WrappedWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final HorizontalListEntry horizontalListEntry) {
            if (horizontalListEntry.flex() != null) {
                horizontalListEntry.flex().reset();
            }
            if (((HorizontalListEntry)widget).alignment() != null) {
                ((HorizontalListEntry)widget).alignment().reset();
            }
            if (((HorizontalListEntry)widget).ignoreWidth() != null) {
                ((HorizontalListEntry)widget).ignoreWidth().reset();
            }
            if (((HorizontalListEntry)widget).ignoreHeight() != null) {
                ((HorizontalListEntry)widget).ignoreHeight().reset();
            }
            if (((HorizontalListEntry)widget).skipFill() != null) {
                ((HorizontalListEntry)widget).skipFill().reset();
            }
        }
        super.reset(widget);
    }
}
