// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ServerInfoWidgetLssPropertyResetter extends ServerEntryWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ServerInfoWidget) {}
        super.reset(widget);
    }
}
