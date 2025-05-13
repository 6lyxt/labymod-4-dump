// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.LabeledWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LabeledWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LabeledWidget && ((LabeledWidget)widget).labelAlignment() != null) {
            ((LabeledWidget)widget).labelAlignment().reset();
        }
        super.reset(widget);
    }
}
