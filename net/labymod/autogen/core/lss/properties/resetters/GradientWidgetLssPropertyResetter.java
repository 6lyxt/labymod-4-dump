// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.store.GradientWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class GradientWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final GradientWidget gradientWidget) {
            if (gradientWidget.direction() != null) {
                gradientWidget.direction().reset();
            }
            if (((GradientWidget)widget).colorStart() != null) {
                ((GradientWidget)widget).colorStart().reset();
            }
            if (((GradientWidget)widget).colorEnd() != null) {
                ((GradientWidget)widget).colorEnd().reset();
            }
        }
        super.reset(widget);
    }
}
