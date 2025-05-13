// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class FoldingWidgetLssPropertyResetter extends ListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final FoldingWidget foldingWidget) {
            if (foldingWidget.contentOffset() != null) {
                foldingWidget.contentOffset().reset();
            }
            if (((FoldingWidget)widget).previewExpand() != null) {
                ((FoldingWidget)widget).previewExpand().reset();
            }
        }
        super.reset(widget);
    }
}
