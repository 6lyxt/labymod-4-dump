// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class DropdownWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final DropdownWidget dropdownWidget) {
            if (dropdownWidget.arrowWidth() != null) {
                dropdownWidget.arrowWidth().reset();
            }
            if (((DropdownWidget)widget).wrapperPadding() != null) {
                ((DropdownWidget)widget).wrapperPadding().reset();
            }
        }
        super.reset(widget);
    }
}
