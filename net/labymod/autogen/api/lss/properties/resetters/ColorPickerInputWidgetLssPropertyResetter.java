// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.ColorPickerInputWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ColorPickerInputWidgetLssPropertyResetter extends FlexibleContentWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ColorPickerInputWidget) {}
        super.reset(widget);
    }
}
