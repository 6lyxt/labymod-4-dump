// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.client.gui.screen.widget.Widget;

public class FlexibleContentEntryLssPropertyResetter extends WrappedWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof FlexibleContentEntry && ((FlexibleContentEntry)widget).ignoreBounds() != null) {
            ((FlexibleContentEntry)widget).ignoreBounds().reset();
        }
        super.reset(widget);
    }
}
