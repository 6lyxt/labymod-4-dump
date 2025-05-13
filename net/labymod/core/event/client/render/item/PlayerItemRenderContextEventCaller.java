// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.item;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;

public class PlayerItemRenderContextEventCaller
{
    public static PlayerItemRenderContextEvent call(final Stack stack, final ItemStack itemStack, final ModelTransformType transformType, final Player player, final PlayerModel playerModel, final int packedLight) {
        return Laby.fireEvent(new PlayerItemRenderContextEvent(stack, itemStack, transformType, player, playerModel, packedLight, Laby.labyAPI().minecraft().getPartialTicks()));
    }
}
