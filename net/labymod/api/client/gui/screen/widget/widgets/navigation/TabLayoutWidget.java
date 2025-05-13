// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
@Link("tabbed.lss")
public class TabLayoutWidget extends HorizontalListWidget
{
    public TabLayoutWidget() {
        ((AbstractWidget<Widget>)this).addId("menu");
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
    }
}
