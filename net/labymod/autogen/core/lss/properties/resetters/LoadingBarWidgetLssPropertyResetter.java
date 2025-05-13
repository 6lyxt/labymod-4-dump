// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.LoadingBarWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LoadingBarWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LoadingBarWidget) {}
        super.reset(widget);
    }
}
