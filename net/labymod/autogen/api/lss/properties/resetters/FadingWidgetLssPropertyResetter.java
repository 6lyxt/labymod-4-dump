// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.transform.FadingWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class FadingWidgetLssPropertyResetter extends WrappedWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof FadingWidget) {}
        super.reset(widget);
    }
}
