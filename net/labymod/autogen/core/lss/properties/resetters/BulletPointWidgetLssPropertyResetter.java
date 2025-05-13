// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class BulletPointWidgetLssPropertyResetter extends AbstractPointWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof BulletPointWidget) {}
        super.reset(widget);
    }
}
