// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ChatWindowRendererWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ChatWindowRendererWidget) {}
        super.reset(widget);
    }
}
