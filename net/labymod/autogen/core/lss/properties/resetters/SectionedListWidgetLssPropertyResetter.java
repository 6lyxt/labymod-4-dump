// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class SectionedListWidgetLssPropertyResetter extends SessionedListWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof SectionedListWidget && ((SectionedListWidget)widget).spaceBetweenEntries() != null) {
            ((SectionedListWidget)widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
