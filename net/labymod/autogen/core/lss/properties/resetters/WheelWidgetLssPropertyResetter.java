// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class WheelWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final WheelWidget wheelWidget) {
            if (wheelWidget.selectable() != null) {
                wheelWidget.selectable().reset();
            }
            if (((WheelWidget)widget).innerRadius() != null) {
                ((WheelWidget)widget).innerRadius().reset();
            }
            if (((WheelWidget)widget).segmentDistanceDegrees() != null) {
                ((WheelWidget)widget).segmentDistanceDegrees().reset();
            }
            if (((WheelWidget)widget).segmentBackgroundColor() != null) {
                ((WheelWidget)widget).segmentBackgroundColor().reset();
            }
            if (((WheelWidget)widget).segmentHighlightColor() != null) {
                ((WheelWidget)widget).segmentHighlightColor().reset();
            }
            if (((WheelWidget)widget).segmentSelectedColor() != null) {
                ((WheelWidget)widget).segmentSelectedColor().reset();
            }
            if (((WheelWidget)widget).segmentBorderColor() != null) {
                ((WheelWidget)widget).segmentBorderColor().reset();
            }
            if (((WheelWidget)widget).innerBackgroundColor() != null) {
                ((WheelWidget)widget).innerBackgroundColor().reset();
            }
            if (((WheelWidget)widget).innerBorderColor() != null) {
                ((WheelWidget)widget).innerBorderColor().reset();
            }
        }
        super.reset(widget);
    }
}
