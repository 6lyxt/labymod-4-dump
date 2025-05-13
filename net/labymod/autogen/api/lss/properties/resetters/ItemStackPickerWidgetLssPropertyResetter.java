// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ItemStackPickerWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ItemStackPickerWidget) {}
        super.reset(widget);
    }
}
