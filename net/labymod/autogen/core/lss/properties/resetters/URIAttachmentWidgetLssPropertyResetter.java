// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.attachment.URIAttachmentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class URIAttachmentWidgetLssPropertyResetter extends AttachmentWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof URIAttachmentWidget) {}
        super.reset(widget);
    }
}
