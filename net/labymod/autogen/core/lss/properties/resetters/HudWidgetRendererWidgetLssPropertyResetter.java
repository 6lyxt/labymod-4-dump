// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.hud.HudWidgetRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HudWidgetRendererWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof HudWidgetRendererWidget) {}
        super.reset(widget);
    }
}
