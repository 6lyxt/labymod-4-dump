// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class StoreItemWidgetLssPropertyResetter extends SimpleWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof final StoreItemWidget storeItemWidget) {
            if (storeItemWidget.installedColor() != null) {
                storeItemWidget.installedColor().reset();
            }
            if (((StoreItemWidget)widget).deletedColor() != null) {
                ((StoreItemWidget)widget).deletedColor().reset();
            }
        }
        super.reset(widget);
    }
}
