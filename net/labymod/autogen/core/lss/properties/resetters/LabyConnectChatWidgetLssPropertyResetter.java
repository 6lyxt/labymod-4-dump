// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LabyConnectChatWidgetLssPropertyResetter extends ChatHistoryWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LabyConnectChatWidget) {}
        super.reset(widget);
    }
}
