// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.positionprovider;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.matrix.Stack;

public interface MinecraftItemPositionProvider
{
    public static final float UNIT = 0.0625f;
    
    void apply(final Stack p0, final PlayerModel p1);
}
