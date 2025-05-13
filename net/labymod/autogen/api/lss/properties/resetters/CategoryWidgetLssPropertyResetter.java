// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CategoryWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class CategoryWidgetLssPropertyResetter extends ButtonWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof CategoryWidget) {}
        super.reset(widget);
    }
}
