// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.util.ChatHistoryWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ChatHistoryWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ChatHistoryWidget && ((ChatHistoryWidget)widget).spaceBetweenEntries() != null) {
            ((ChatHistoryWidget)widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
