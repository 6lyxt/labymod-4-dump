// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.context.ContextMenuEntryWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ContextMenuEntryWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ContextMenuEntryWidget) {}
        super.reset(widget);
    }
}
