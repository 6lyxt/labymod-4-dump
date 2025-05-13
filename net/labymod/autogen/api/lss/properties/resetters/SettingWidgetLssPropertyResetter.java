// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SettingWidgetLssPropertyResetter extends FlexibleContentWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SettingWidget) {}
        super.reset(widget);
    }
}
