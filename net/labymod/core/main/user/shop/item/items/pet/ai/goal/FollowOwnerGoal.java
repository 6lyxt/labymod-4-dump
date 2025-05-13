// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.goal;

import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.api.client.entity.player.Player;
import java.util.function.Supplier;

public class FollowOwnerGoal extends Goal
{
    private final Supplier<Player> owner;
    private final float startDistance;
    private final float stopDistance;
    private int ticks;
    
    public FollowOwnerGoal(final PetBehavior behavior, final Supplier<Player> owner, final float startDistance, final float stopDistance) {
        super(behavior);
        this.setActions(GoalAction.MOVE);
        this.owner = owner;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
    }
    
    @Override
    public boolean canUse() {
        final Player player = this.owner.get();
        return player != null && player.gameMode() != GameMode.SPECTATOR && this.getDistanceSquared() >= this.getStartDistance(player.abilities().flying().get());
    }
    
    @Override
    public boolean canContinueToUse() {
        return this.getDistanceSquared() > this.stopDistance * this.stopDistance;
    }
    
    public float getStartDistance(final boolean flying) {
        float distance = this.startDistance;
        if (flying) {
            distance /= 2.0f;
        }
        return distance * distance;
    }
    
    @Override
    public void tick() {
        final Player player = this.owner.get();
        final PetBehavior behavior = this.behavior();
        final DoubleVector3 targetPosition = this.getTargetPosition();
        final DoubleVector3 currentPosition = this.behavior().position();
        DoubleVector3 direction = targetPosition.sub(currentPosition).normalize();
        direction.setY(0.0);
        final float timePassed = this.ticks * 20.0f;
        final float strength = (float)Math.cos(0.006283185307179587 * timePassed) / 5.0f;
        final DoubleVector3 prevDirection = behavior.lookController().lookAt().copy().normalize();
        final double pX = prevDirection.getZ();
        final double pZ = prevDirection.getX();
        direction = direction.add(pX * strength, 0.0, pZ * strength);
        final float speed = behavior.getMovementSpeed() * behavior.findWalkType(player).getMultiplier();
        final DoubleVector3 offset = direction.multiply(speed);
        behavior.addDeltaMovement(offset);
        ++this.ticks;
        final double targetX = currentPosition.getX() + offset.getX();
        final double targetY = currentPosition.getY() + offset.getY() + player.getEyeHeight();
        final double targetZ = currentPosition.getZ() + offset.getZ();
        this.behavior().lookController().setLookAt(targetX, targetY, targetZ, 7.5f);
    }
    
    private DoubleVector3 getTargetPosition() {
        final int petIndex = this.behavior().getPetIndex();
        final int maxPets = this.behavior().getMaxPets();
        final Player player = this.owner.get();
        if (player == null) {
            return this.behavior().position();
        }
        final Position chasingPos = player.chasingPosition();
        final Position prevChasingPos = player.previousChasingPosition();
        final double angle = MathHelper.toDegreesDouble(Math.atan2(prevChasingPos.getZ() - chasingPos.getZ(), prevChasingPos.getX() - chasingPos.getX()));
        final double fullCircle = 180.0;
        final double radius = 1.5;
        final double areaRotation = MathHelper.toRadiansDouble(((maxPets > 1) ? (angle + fullCircle / (maxPets - 1) * petIndex) : angle) - 90.0);
        final double petOffsetX = Math.cos(areaRotation) * radius;
        final double petOffsetZ = Math.sin(areaRotation) * radius;
        final double x = prevChasingPos.getX() + petOffsetX;
        final double y = prevChasingPos.getY();
        final double z = prevChasingPos.getZ() + petOffsetZ;
        return new DoubleVector3(x, y, z);
    }
    
    private double getDistanceSquared() {
        final DoubleVector3 current = this.behavior().position();
        final double currentX = current.getX();
        final double currentZ = current.getZ();
        final DoubleVector3 targetPosition = this.getTargetPosition();
        final double goalX = targetPosition.getX();
        final double goalZ = targetPosition.getZ();
        return (goalX - currentX) * (goalX - currentX) + (goalZ - currentZ) * (goalZ - currentZ);
    }
}
