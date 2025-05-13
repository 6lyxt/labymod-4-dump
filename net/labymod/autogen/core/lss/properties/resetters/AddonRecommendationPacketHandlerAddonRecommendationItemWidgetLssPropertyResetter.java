// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation.AddonRecommendationPacketHandler;
import net.labymod.api.client.gui.screen.widget.Widget;

public class AddonRecommendationPacketHandlerAddonRecommendationItemWidgetLssPropertyResetter extends StoreItemWidgetLssPropertyResetter
{
    @Override
    public void reset(final Widget widget) {
        if (widget instanceof AddonRecommendationPacketHandler.AddonRecommendationItemWidget) {}
        super.reset(widget);
    }
}
