// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SwitchWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SwitchWidget && ((SwitchWidget)widget).textHoverColor() != null) {
            ((SwitchWidget)widget).textHoverColor().reset();
        }
        super.reset(widget);
    }
}
