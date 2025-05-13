// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class BulletPointRendererWidgetLssPropertyResetter extends AbstractCirclePointWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof BulletPointRendererWidget) {}
        super.reset(widget);
    }
}
