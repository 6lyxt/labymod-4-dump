// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownEntryWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class DropdownEntryWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof DropdownEntryWidget && ((DropdownEntryWidget)widget).fontSize() != null) {
            ((DropdownEntryWidget)widget).fontSize().reset();
        }
        super.reset(widget);
    }
}
