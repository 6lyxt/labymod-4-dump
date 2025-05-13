// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.model.entity.player;

import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.client.render.RenderEvent;

public class PlayerCapeRenderEvent extends RenderEvent implements Cancellable
{
    private final Player player;
    private final PlayerModel playerModel;
    private boolean cancelled;
    
    public PlayerCapeRenderEvent(@NotNull final Player player, @NotNull final PlayerModel playerModel, @NotNull final Stack stack, @NotNull final Phase phase) {
        super(stack, phase);
        this.player = player;
        this.playerModel = playerModel;
    }
    
    @NotNull
    public Player player() {
        return this.player;
    }
    
    @NotNull
    public PlayerModel playerModel() {
        return this.playerModel;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
