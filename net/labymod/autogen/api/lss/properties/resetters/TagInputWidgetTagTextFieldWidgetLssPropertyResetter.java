// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TagInputWidgetTagTextFieldWidgetLssPropertyResetter extends TextFieldWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TagInputWidget.TagTextFieldWidget) {}
        super.reset(widget);
    }
}
