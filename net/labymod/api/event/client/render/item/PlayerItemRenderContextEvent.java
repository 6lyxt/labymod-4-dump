// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.item;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

@ApiStatus.Internal
public class PlayerItemRenderContextEvent extends DefaultCancellable implements Event
{
    private final Stack stack;
    private final ItemStack itemStack;
    private final ModelTransformType type;
    private final Player player;
    private final PlayerModel playerModel;
    private final int packedLight;
    private final float partialTicks;
    
    public PlayerItemRenderContextEvent(@NotNull final Stack stack, @NotNull final ItemStack itemStack, @NotNull final ModelTransformType type, @NotNull final Player player, @NotNull final PlayerModel playerModel, final int packedLight, final float partialTicks) {
        this.stack = stack;
        this.itemStack = itemStack;
        this.type = type;
        this.player = player;
        this.playerModel = playerModel;
        this.packedLight = packedLight;
        this.partialTicks = partialTicks;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public ItemStack itemStack() {
        return this.itemStack;
    }
    
    public ModelTransformType transformType() {
        return this.type;
    }
    
    public Player player() {
        return this.player;
    }
    
    public PlayerModel playerModel() {
        return this.playerModel;
    }
    
    public int getPackedLight() {
        return this.packedLight;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
