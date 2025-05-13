// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.TreeWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TreeWidgetLssPropertyResetter extends VerticalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TreeWidget) {}
        super.reset(widget);
    }
}
