// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.SettingLikeWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SettingLikeWidgetLssPropertyResetter extends HorizontalListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SettingLikeWidget) {}
        super.reset(widget);
    }
}
