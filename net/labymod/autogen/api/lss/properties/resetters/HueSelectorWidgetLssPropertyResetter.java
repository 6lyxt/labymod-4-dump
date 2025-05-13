// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.HueSelectorWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HueSelectorWidgetLssPropertyResetter extends SelectorWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof HueSelectorWidget) {}
        super.reset(widget);
    }
}
