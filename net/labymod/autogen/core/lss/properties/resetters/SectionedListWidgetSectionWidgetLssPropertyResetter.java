// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SectionedListWidgetSectionWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final SectionedListWidget.SectionWidget sectionWidget) {
            if (sectionWidget.lineHeight() != null) {
                sectionWidget.lineHeight().reset();
            }
            if (((SectionedListWidget.SectionWidget)widget).lineColor() != null) {
                ((SectionedListWidget.SectionWidget)widget).lineColor().reset();
            }
            if (((SectionedListWidget.SectionWidget)widget).textColor() != null) {
                ((SectionedListWidget.SectionWidget)widget).textColor().reset();
            }
        }
        super.reset(widget);
    }
}
