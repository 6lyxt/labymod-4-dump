// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.model.entity.player;

import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;

public class PlayerModelRenderHandEvent extends PlayerModelRenderEvent
{
    private final MainHand hand;
    
    public PlayerModelRenderHandEvent(@NotNull final Player player, @NotNull final PlayerModel model, @NotNull final Stack stack, @NotNull final Phase phase, @NotNull final MainHand hand) {
        this(player, model, stack, phase, hand, -1);
    }
    
    public PlayerModelRenderHandEvent(@NotNull final Player player, @NotNull final PlayerModel model, @NotNull final Stack stack, @NotNull final Phase phase, @NotNull final MainHand hand, final int packedLight) {
        super(player, model, stack, phase, packedLight);
        this.hand = hand;
    }
    
    @NotNull
    public MainHand hand() {
        return this.hand;
    }
}
