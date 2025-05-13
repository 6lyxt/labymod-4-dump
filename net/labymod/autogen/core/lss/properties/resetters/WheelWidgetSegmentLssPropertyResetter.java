// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WheelWidgetSegmentLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof WheelWidget.Segment && ((WheelWidget.Segment)widget).segmentColor() != null) {
            ((WheelWidget.Segment)widget).segmentColor().reset();
        }
        super.reset(widget);
    }
}
