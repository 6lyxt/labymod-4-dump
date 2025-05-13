// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressableProgressBarWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class ProgressableProgressBarWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final ProgressableProgressBarWidget progressableProgressBarWidget) {
            if (progressableProgressBarWidget.progressForegroundColor() != null) {
                progressableProgressBarWidget.progressForegroundColor().reset();
            }
            if (((ProgressableProgressBarWidget)widget).progressBackgroundColor() != null) {
                ((ProgressableProgressBarWidget)widget).progressBackgroundColor().reset();
            }
        }
        super.reset(widget);
    }
}
