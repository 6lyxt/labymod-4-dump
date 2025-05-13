// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.context.ContextMenuListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ContextMenuListWidgetLssPropertyResetter extends VerticalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ContextMenuListWidget) {}
        super.reset(widget);
    }
}
