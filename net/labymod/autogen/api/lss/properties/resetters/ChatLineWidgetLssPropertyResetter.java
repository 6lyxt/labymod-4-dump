// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatLineWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ChatLineWidgetLssPropertyResetter extends FadingWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ChatLineWidget) {}
        super.reset(widget);
    }
}
