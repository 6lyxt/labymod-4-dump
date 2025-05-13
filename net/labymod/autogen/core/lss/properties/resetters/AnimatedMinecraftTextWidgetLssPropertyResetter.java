// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic.AnimatedMinecraftTextWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AnimatedMinecraftTextWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AnimatedMinecraftTextWidget) {}
        super.reset(widget);
    }
}
