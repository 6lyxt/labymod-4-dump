// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AbstractSidebarActivitySidebarButtonWidgetLssPropertyResetter extends ButtonWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AbstractSidebarActivity.SidebarButtonWidget) {}
        super.reset(widget);
    }
}
