// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.TileWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TileWidgetLssPropertyResetter extends WrappedWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TileWidget) {}
        super.reset(widget);
    }
}
