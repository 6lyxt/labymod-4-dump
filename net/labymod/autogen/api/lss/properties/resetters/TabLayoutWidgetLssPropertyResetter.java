// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabLayoutWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TabLayoutWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TabLayoutWidget) {}
        super.reset(widget);
    }
}
