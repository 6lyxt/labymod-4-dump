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

public class PlayerModelRenderEvent extends RenderEvent implements Cancellable
{
    private final Player player;
    private final PlayerModel model;
    private final int packedLight;
    private boolean cancelled;
    
    public PlayerModelRenderEvent(@NotNull final Player player, @NotNull final PlayerModel model, @NotNull final Stack stack, @NotNull final Phase phase) {
        this(player, model, stack, phase, -1);
    }
    
    public PlayerModelRenderEvent(@NotNull final Player player, @NotNull final PlayerModel model, @NotNull final Stack stack, @NotNull final Phase phase, final int packedLight) {
        super(stack, phase);
        this.player = player;
        this.model = model;
        this.packedLight = packedLight;
    }
    
    @NotNull
    public Player player() {
        return this.player;
    }
    
    @NotNull
    public PlayerModel model() {
        return this.model;
    }
    
    public int getPackedLight() {
        return this.packedLight;
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
