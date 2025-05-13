// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SessionedListWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SessionedListWidget) {}
        super.reset(widget);
    }
}
