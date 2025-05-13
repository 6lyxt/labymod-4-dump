// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge.renderer.positions;

import java.util.Iterator;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.badge.renderer.PositionRenderer;

public class OverwritePingRenderer implements PositionRenderer
{
    @Override
    public int render(final Stack stack, final float x, final float y, final NetworkPlayerInfo player, final List<KeyValue<BadgeRenderer>> badges) {
        boolean rendered = false;
        for (final KeyValue<BadgeRenderer> badge : badges) {
            final BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player, true)) {
                renderer.render(stack, x, y, player);
                rendered = true;
            }
        }
        return rendered ? 8 : 0;
    }
    
    @Override
    public int getWidth(final NetworkPlayerInfo player, final List<KeyValue<BadgeRenderer>> badges, final boolean fromCache) {
        for (final KeyValue<BadgeRenderer> badge : badges) {
            final BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player, fromCache)) {
                return 8;
            }
        }
        return 0;
    }
}
