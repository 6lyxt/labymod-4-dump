// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ChatWindowWidgetLssPropertyResetter extends NewWindowWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ChatWindowWidget) {}
        super.reset(widget);
    }
}
