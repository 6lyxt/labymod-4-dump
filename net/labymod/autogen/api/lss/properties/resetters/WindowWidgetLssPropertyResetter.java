// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.WindowWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WindowWidgetLssPropertyResetter extends InterpolateWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof WindowWidget) {}
        super.reset(widget);
    }
}
