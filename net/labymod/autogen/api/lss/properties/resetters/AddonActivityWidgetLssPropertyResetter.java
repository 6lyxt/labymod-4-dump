// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.AddonActivityWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AddonActivityWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AddonActivityWidget) {}
        super.reset(widget);
    }
}
