// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.navigation.PageWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class PageWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof PageWidget) {}
        super.reset(widget);
    }
}
