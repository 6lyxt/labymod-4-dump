// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.SplitContentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SplitContentWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final SplitContentWidget splitContentWidget) {
            if (splitContentWidget.splitButtonWidth() != null) {
                splitContentWidget.splitButtonWidth().reset();
            }
            if (((SplitContentWidget)widget).splitGapWidth() != null) {
                ((SplitContentWidget)widget).splitGapWidth().reset();
            }
            if (((SplitContentWidget)widget).initialPercentage() != null) {
                ((SplitContentWidget)widget).initialPercentage().reset();
            }
            if (((SplitContentWidget)widget).minPercentage() != null) {
                ((SplitContentWidget)widget).minPercentage().reset();
            }
            if (((SplitContentWidget)widget).maxPercentage() != null) {
                ((SplitContentWidget)widget).maxPercentage().reset();
            }
        }
        super.reset(widget);
    }
}
