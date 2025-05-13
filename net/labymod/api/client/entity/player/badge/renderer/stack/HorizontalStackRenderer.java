// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge.renderer.stack;

import java.util.Iterator;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.badge.renderer.PositionRenderer;

public class HorizontalStackRenderer implements PositionRenderer
{
    private final boolean stackToLeft;
    
    public HorizontalStackRenderer(final boolean stackToLeft) {
        this.stackToLeft = stackToLeft;
    }
    
    @Override
    public int render(final Stack stack, float x, final float y, final NetworkPlayerInfo player, final List<KeyValue<BadgeRenderer>> badges) {
        int width = 0;
        if (this.stackToLeft) {
            x -= 8.0f;
        }
        for (final KeyValue<BadgeRenderer> badge : badges) {
            final BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player, true)) {
                final float xPosition = this.stackToLeft ? (x - width) : (x + width);
                renderer.render(stack, xPosition, y, player);
                width += renderer.getSize() + 1;
            }
        }
        return width;
    }
    
    @Override
    public int getWidth(final NetworkPlayerInfo player, final List<KeyValue<BadgeRenderer>> badges, final boolean fromCache) {
        int width = 0;
        for (final KeyValue<BadgeRenderer> badge : badges) {
            final BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player, fromCache)) {
                width += renderer.getSize() + 1;
            }
        }
        return width;
    }
}
