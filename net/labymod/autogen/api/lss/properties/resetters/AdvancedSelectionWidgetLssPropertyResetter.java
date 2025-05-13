// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AdvancedSelectionWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AdvancedSelectionWidget) {}
        super.reset(widget);
    }
}
