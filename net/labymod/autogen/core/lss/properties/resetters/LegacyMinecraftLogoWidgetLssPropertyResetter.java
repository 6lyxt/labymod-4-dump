// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.title.header.type.LegacyMinecraftLogoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LegacyMinecraftLogoWidgetLssPropertyResetter extends MinecraftLogoWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof LegacyMinecraftLogoWidget) {}
        super.reset(widget);
    }
}
