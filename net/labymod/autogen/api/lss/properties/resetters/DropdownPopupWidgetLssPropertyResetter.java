// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownPopupWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class DropdownPopupWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof DropdownPopupWidget) {}
        super.reset(widget);
    }
}
