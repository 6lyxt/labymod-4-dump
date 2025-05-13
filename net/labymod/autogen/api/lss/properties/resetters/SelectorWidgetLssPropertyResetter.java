// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SelectorWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SelectorWidget && ((SelectorWidget)widget).orientation() != null) {
            ((SelectorWidget)widget).orientation().reset();
        }
        super.reset(widget);
    }
}
