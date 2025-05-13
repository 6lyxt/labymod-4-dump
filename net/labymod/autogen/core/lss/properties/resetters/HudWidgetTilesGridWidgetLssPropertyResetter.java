// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTilesGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

public class HudWidgetTilesGridWidgetLssPropertyResetter extends AbstractWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof HudWidgetTilesGridWidget && ((HudWidgetTilesGridWidget)widget).spaceBetweenEntries() != null) {
            ((HudWidgetTilesGridWidget)widget).spaceBetweenEntries().reset();
        }
        super.reset(widget);
    }
}
