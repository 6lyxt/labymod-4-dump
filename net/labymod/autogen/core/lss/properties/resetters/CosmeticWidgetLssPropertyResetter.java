// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class CosmeticWidgetLssPropertyResetter extends FlexibleContentWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof CosmeticWidget) {}
        super.reset(widget);
    }
}
