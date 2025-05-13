// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.GridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class GridWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final GridWidget gridWidget) {
            if (gridWidget.outlineThickness() != null) {
                gridWidget.outlineThickness().reset();
            }
            if (((GridWidget)widget).columns() != null) {
                ((GridWidget)widget).columns().reset();
            }
            if (((GridWidget)widget).rows() != null) {
                ((GridWidget)widget).rows().reset();
            }
            if (((GridWidget)widget).maxColumnWidth() != null) {
                ((GridWidget)widget).maxColumnWidth().reset();
            }
            if (((GridWidget)widget).maxRowHeight() != null) {
                ((GridWidget)widget).maxRowHeight().reset();
            }
            if (((GridWidget)widget).iterate() != null) {
                ((GridWidget)widget).iterate().reset();
            }
            if (((GridWidget)widget).auto() != null) {
                ((GridWidget)widget).auto().reset();
            }
        }
        super.reset(widget);
    }
}
