// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.model.CosmeticModelWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class CosmeticModelWidgetLssPropertyResetter extends ModelWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof CosmeticModelWidget) {}
        super.reset(widget);
    }
}
