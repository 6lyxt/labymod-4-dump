// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ModelWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ModelWidget && ((ModelWidget)widget).modelColor() != null) {
            ((ModelWidget)widget).modelColor().reset();
        }
        super.reset(widget);
    }
}
