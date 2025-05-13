// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HrWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof HrWidget) {}
        super.reset(widget);
    }
}
