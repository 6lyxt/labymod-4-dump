// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LabyNetServerInfoWidgetLssPropertyResetter extends ServerInfoWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LabyNetServerInfoWidget) {}
        super.reset(widget);
    }
}
