// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.positionprovider;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.matrix.Stack;

public class HideItemPositionProvider implements MinecraftItemPositionProvider
{
    @Override
    public void apply(final Stack stack, final PlayerModel playerModel) {
        stack.scale(0.0f, 0.0f, 0.0f);
    }
}
