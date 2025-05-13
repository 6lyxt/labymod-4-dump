// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class TagInputWidgetLssPropertyResetter extends WrappedListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof TagInputWidget) {}
        super.reset(widget);
    }
}
