// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.widgets.layout.IconSliderWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class IconSliderWidgetIconSliderNavigationWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof IconSliderWidget.IconSliderNavigationWidget && ((IconSliderWidget.IconSliderNavigationWidget)widget).spaceBetweenEntries() != null) {
            ((IconSliderWidget.IconSliderNavigationWidget)widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
