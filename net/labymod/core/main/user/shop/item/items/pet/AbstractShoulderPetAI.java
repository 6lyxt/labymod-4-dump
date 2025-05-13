// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.item.model.type.MoveType;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.api.Laby;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.main.user.shop.item.items.PetItem;

@Deprecated
public abstract class AbstractShoulderPetAI implements PetAI
{
    protected final PetItem pet;
    private final float flightAltitude;
    
    protected AbstractShoulderPetAI(final PetItem pet) {
        this.pet = pet;
        this.flightAltitude = 0.5f;
    }
    
    @Override
    public void earlyRender(final PlayerModel playerModel, final Player player, final PetDataStorage petDataStorage, final Stack stack, final float partialTicks) {
        final float currentTick = player.getRenderTick(partialTicks);
        final float walkingSpeed = player.getWalkingSpeed(partialTicks);
        final DoubleVector3 offset = petDataStorage.getOffset();
        final DoubleVector3 previousPosition = petDataStorage.getPreviousPosition();
        final DoubleVector3 position = petDataStorage.getPosition();
        if (previousPosition.distanceSquared(position) > 10.0) {
            this.tick(playerModel, player, petDataStorage, currentTick, partialTicks);
        }
        final float renderYaw = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        final Position playerPosition = player.position();
        final Position playerPreviousPosition = player.previousPosition();
        final double playerX = playerPosition.lerpX(playerPreviousPosition, partialTicks);
        final double playerY = playerPosition.lerpY(playerPreviousPosition, partialTicks);
        final double playerZ = playerPosition.lerpZ(playerPreviousPosition, partialTicks);
        final double x = position.lerpX(previousPosition, partialTicks);
        final double y = position.lerpY(previousPosition, partialTicks);
        final double z = position.lerpZ(previousPosition, partialTicks);
        stack.translate(offset.getX(), offset.getY(), offset.getZ());
        final boolean rightShoulder = petDataStorage.isRightShoulder();
        final ModelPart armPart = rightShoulder ? playerModel.getRightArm() : playerModel.getLeftArm();
        final float positionScale = 1.0f / Laby.labyAPI().renderPipeline().renderConstants().modelScale();
        final FloatVector3 translation = armPart.getModelPartTransform().getTranslation();
        stack.translate(translation.getX() * positionScale, (translation.getY() + ((playerModel instanceof DummyPlayerModel) ? 6.25f : 0.0f)) * positionScale, translation.getZ() * positionScale);
        if (this.isAlwaysAttachedToArm() || petDataStorage.isAttachedToOwner()) {
            final Transformation transformation = armPart.getAnimationTransformation();
            final FloatVector3 animationTranslation = transformation.getTranslation();
            stack.translate(animationTranslation.getX() * positionScale, animationTranslation.getY() * positionScale, animationTranslation.getZ() * positionScale);
            final FloatVector3 rotation = transformation.getRotation();
            final float transition = Math.min(1.0f, 1.0f - player.getWalkingSpeed(partialTicks));
            stack.rotateRadians(rotation.getZ() * transition, 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY() * transition, 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX() * transition, 1.0f, 0.0f, 0.0f);
            final FloatVector3 scale = transformation.getScale();
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        stack.translate((rightShoulder ? -1.0f : 1.0f) * positionScale, -2.24f * positionScale, -1.0f * positionScale);
        stack.translate(0.0f, 0.015f, 0.0f);
        if (player.isWearingTop() && !player.isWearingElytra()) {
            stack.translate(0.0f, -0.06f, 0.0f);
        }
        this.onAbsoluteMovement(stack, petDataStorage, x, y, z, playerX, playerY, playerZ, renderYaw);
        if (!player.isCrouching() && this.shouldBounce(petDataStorage)) {
            final double uniqueTick = currentTick + this.pet.getIdentifier() * 3.141592653589793;
            stack.translate(0.0f, (float)(-Math.abs(Math.cos(uniqueTick / 2.0) / 3.0 * walkingSpeed / 2.0)), 0.0f);
            stack.rotateRadians((float)Math.toRadians(Math.cos(uniqueTick / 2.0) * 10.0 * walkingSpeed), 0.0f, 0.0f, 1.0f);
            final double chasingY = player.chasingPosition().lerpY(player.previousChasingPosition(), partialTicks);
            final double velocityY = (playerY > chasingY) ? 0.0 : (playerY - chasingY);
            final double bounce = Math.abs(Math.cos(uniqueTick / 2.0 + velocityY) / 20.0);
            stack.translate(0.0f, (float)(bounce * velocityY), 0.0f);
        }
    }
    
    @Override
    public void tick(final PlayerModel playerModel, final Player player, final PetDataStorage petDataStorage, final float currentTick, final float partialTicks) {
        final float walkingSpeed = player.getWalkingSpeed(partialTicks);
        final boolean isMoving = walkingSpeed > 0.3f || (this.shouldTriggerOnSwing() && player.isSwingingHand());
        final MoveType moveType = this.pet.itemDetails().getMoveType();
        if (!petDataStorage.isMoving() && moveType == MoveType.MOVE_ONLY) {
            petDataStorage.updateMovingState(true);
        }
        if (moveType.canIdle()) {
            if (isMoving && petDataStorage.isAttachedToOwner() && moveType.canMove()) {
                petDataStorage.updateMovingState(true);
                this.pet.playPetAnimation(AnimationTrigger.START_MOVING, petDataStorage, player);
            }
        }
        else if (isMoving && petDataStorage.isFakeIdle() && moveType.canMove()) {
            petDataStorage.setFakeIdle(false);
            this.pet.playPetAnimation(AnimationTrigger.START_MOVING, petDataStorage, player);
        }
        if (petDataStorage.isAttachedToOwner() || isMoving) {
            petDataStorage.setTimeMovedChangedAt(TimeUtil.getMillis());
        }
        final long timePassedSinceChange = TimeUtil.getMillis() - petDataStorage.getFlyingChangedAt();
        final float sigmoidInput = 0.006f * Math.min(1000L, timePassedSinceChange);
        final float increasing = (float)((1.0 - Math.exp(-sigmoidInput)) / (1.0 + Math.exp(-sigmoidInput)) * 1.0);
        final float decreasing = 1.0f - increasing;
        final Position chasingPosition = player.chasingPosition();
        final double chasingX = chasingPosition.getX();
        final double chasingY = chasingPosition.getY() + this.flightAltitude;
        final double chasingZ = chasingPosition.getZ();
        final Position position = player.position();
        final double posX = position.getX();
        final double posY = position.getY();
        final double posZ = position.getZ();
        final double positionFactor = petDataStorage.isMoving() ? decreasing : ((double)increasing);
        final double chasingFactor = petDataStorage.isMoving() ? increasing : ((double)decreasing);
        final double x = posX * positionFactor + chasingX * chasingFactor;
        final double y = posY * positionFactor + chasingY * chasingFactor;
        final double z = posZ * positionFactor + chasingZ * chasingFactor;
        final float renderYaw = player.getBodyRotationY();
        final float lookYaw = (float)(-Math.toDegrees(Math.atan2(-x + posX, -z + posZ)));
        final float sigmoidInputTransition = Math.min(2000L, TimeUtil.getMillis() - petDataStorage.getAboveShoulderChangedAt()) / 2000.0f * 6.0f;
        final float transition = (float)((1.0 - Math.exp(-sigmoidInputTransition)) / (1.0 + Math.exp(-sigmoidInputTransition)) * 1.0);
        final float factor = petDataStorage.isAboveShoulder() ? (1.0f - transition) : transition;
        final float modDiff = (renderYaw - lookYaw) % 360.0f;
        final float shortestDistance = 180.0f - Math.abs(Math.abs(modDiff) - 180.0f);
        final float distance = ((modDiff + 360.0f) % 360.0f < 180.0f) ? shortestDistance : (shortestDistance * -1.0f);
        final float rotationY = renderYaw - distance * factor;
        petDataStorage.teleport(x, y, z, 0.0f, rotationY, 0.0f);
        if (moveType.canIdle()) {
            if (petDataStorage.isMoving()) {
                final boolean aboveShoulder = walkingSpeed < 0.1f || chasingFactor < 0.8999999761581421;
                if (aboveShoulder != petDataStorage.isAboveShoulder()) {
                    petDataStorage.setAboveShoulder(aboveShoulder);
                    petDataStorage.setAboveShoulderChangedAt(TimeUtil.getMillis());
                }
                if (petDataStorage.isAboveShoulder() && petDataStorage.getTimeMovedChangedAt() + this.getFakeIdleDelay() < TimeUtil.getMillis() && moveType.canIdle()) {
                    petDataStorage.updateMovingState(false);
                    this.pet.playPetAnimation(AnimationTrigger.STOP_MOVING, petDataStorage, player);
                }
            }
            else {
                petDataStorage.setAboveShoulder(true);
            }
        }
        else {
            final boolean aboveShoulder = walkingSpeed < 0.1f || chasingFactor < 0.8999999761581421;
            if (aboveShoulder != petDataStorage.isAboveShoulder()) {
                petDataStorage.setAboveShoulder(aboveShoulder);
                petDataStorage.setAboveShoulderChangedAt(TimeUtil.getMillis());
            }
            if (!petDataStorage.isFakeIdle() && petDataStorage.getTimeMovedChangedAt() + this.getFakeMoveDelay() < TimeUtil.getMillis()) {
                petDataStorage.setFakeIdle(true);
                this.pet.playPetAnimation(AnimationTrigger.STOP_MOVING, petDataStorage, player);
            }
        }
    }
    
    @Override
    public boolean canAttach() {
        return this.pet.itemDetails().getMoveType().canIdle();
    }
    
    protected boolean shouldBounce(final PetDataStorage storage) {
        return storage.isAttachedToOwner();
    }
    
    protected boolean isAlwaysAttachedToArm() {
        return false;
    }
    
    protected boolean shouldTriggerOnSwing() {
        return true;
    }
    
    protected abstract long getFakeIdleDelay();
    
    protected abstract long getFakeMoveDelay();
    
    protected abstract void onAbsoluteMovement(final Stack p0, final PetDataStorage p1, final double p2, final double p3, final double p4, final double p5, final double p6, final double p7, final float p8);
}
