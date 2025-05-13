// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.api.client.entity.player.Player;
import java.util.function.Supplier;

public class TeleportGoal extends Goal
{
    private final Supplier<Player> owner;
    private final float teleportDistance;
    
    public TeleportGoal(final PetBehavior behavior, final Supplier<Player> owner, final float teleportDistance) {
        super(behavior);
        this.owner = owner;
        this.teleportDistance = teleportDistance;
    }
    
    @Override
    public boolean canUse() {
        final Player player = this.owner.get();
        return player != null && player.gameMode() != GameMode.SPECTATOR && player.isOnGround() && this.getDistanceSquared(player) >= this.getTeleportDistance();
    }
    
    @Override
    public void start() {
        super.start();
        final Player player = this.owner.get();
        this.behavior().teleportTo(player);
    }
    
    private float getTeleportDistance() {
        return MathHelper.square(this.teleportDistance);
    }
    
    private float getDistanceSquared(final Player player) {
        return (float)player.getDistanceSquared(this.behavior().position());
    }
}
