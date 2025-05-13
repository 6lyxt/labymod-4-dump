// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotScrollbarWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ScreenshotScrollbarWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof ScreenshotScrollbarWidget) {}
        super.reset(widget);
    }
}
