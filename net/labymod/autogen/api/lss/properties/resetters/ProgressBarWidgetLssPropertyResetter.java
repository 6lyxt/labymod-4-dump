// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressBarWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ProgressBarWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ProgressBarWidget) {}
        super.reset(widget);
    }
}
