// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import net.labymod.api.client.world.ClientWorld;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.api.client.entity.player.Player;

public class LookAtPlayerGoal extends Goal
{
    private static final String HEAD_NAME = "head";
    private final float lookDistance;
    private Player lookAt;
    private int lookTime;
    
    public LookAtPlayerGoal(final PetBehavior behavior, final float lookDistance) {
        super(behavior);
        this.setActions(GoalAction.LOOK);
        this.lookDistance = lookDistance;
    }
    
    @Override
    public void start() {
        this.lookTime = this.adjustedTickDelay(40 + LookAtPlayerGoal.RANDOM.nextInt(40));
    }
    
    @Override
    public void stop() {
        this.lookAt = null;
        this.behavior().lookController().setLookAt(this.behavior().findRandomPointOfInterest(LookAtPlayerGoal.RANDOM));
    }
    
    @Override
    public void tick() {
        if (this.lookAt.isDead()) {
            return;
        }
        final Position position = this.lookAt.position();
        final double targetX = position.getX();
        final double targetY = position.getY() + this.lookAt.getEyeHeight();
        final double targetZ = position.getZ();
        this.behavior().lookController().setLookAt(targetX, targetY, targetZ);
        --this.lookTime;
    }
    
    @Override
    public boolean canUse() {
        if (this.behavior().isWalking() || LookAtPlayerGoal.RANDOM.nextFloat() >= 0.02f) {
            return false;
        }
        final ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
        this.lookAt = level.getNearestEntity((List<? extends Player>)level.getPlayers(), this.behavior().position());
        return this.lookAt != null;
    }
    
    @Override
    public boolean canContinueToUse() {
        return !this.lookAt.isDead() && this.lookAt.getDistanceSquared(this.behavior().position()) <= this.lookDistance * this.lookDistance && this.lookTime > 0;
    }
}
