// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.ComponentStatusContainerWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ComponentStatusContainerWidgetLssPropertyResetter extends StatusContainerWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ComponentStatusContainerWidget) {}
        super.reset(widget);
    }
}
