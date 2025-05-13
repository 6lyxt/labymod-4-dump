// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.labyconnect.chat.attachment.AttachmentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AttachmentWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AttachmentWidget) {}
        super.reset(widget);
    }
}
