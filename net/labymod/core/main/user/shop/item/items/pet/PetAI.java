// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Deprecated
public interface PetAI
{
    void earlyRender(final PlayerModel p0, final Player p1, final PetDataStorage p2, final Stack p3, final float p4);
    
    void tick(final PlayerModel p0, final Player p1, final PetDataStorage p2, final float p3, final float p4);
    
    default boolean canAttach() {
        return true;
    }
}
