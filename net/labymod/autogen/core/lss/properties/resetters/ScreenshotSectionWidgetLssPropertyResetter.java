// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotSectionWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ScreenshotSectionWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ScreenshotSectionWidget) {}
        super.reset(widget);
    }
}
