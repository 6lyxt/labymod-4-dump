// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.LiveServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LiveServerInfoWidgetLssPropertyResetter extends ServerInfoWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LiveServerInfoWidget) {}
        super.reset(widget);
    }
}
