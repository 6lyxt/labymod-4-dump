// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.controller;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;

public class JumpController
{
    private static final float SPEED_SCALE = 7.0f;
    private static final float JUMP_STRENGTH = 1.2f;
    private final PetBehavior behavior;
    private final ClientWorld level;
    private int jumpTrigger;
    
    public JumpController(final PetBehavior behavior, final ClientWorld level) {
        this.behavior = behavior;
        this.level = level;
    }
    
    public void tick() {
        if (this.jumpTrigger > 0) {
            --this.jumpTrigger;
        }
    }
    
    public boolean canAutoJump() {
        return this.behavior.onGround() && this.jumpTrigger <= 0;
    }
    
    public boolean canJump(final FloatVector2 inputVector, final float x, final float z, final float rotationYaw) {
        if (!this.canAutoJump()) {
            return false;
        }
        final DoubleVector3 position = this.behavior.position().copy();
        final DoubleVector3 positionWithOffset = position.copy().add(x, 0.0, z);
        DoubleVector3 offset = new DoubleVector3(x, 0.0, z);
        float magnitude = (float)offset.lengthSquared();
        final float speed = this.behavior.getMovementSpeed();
        if (magnitude <= 0.001f) {
            final float velocityX = speed * inputVector.getX();
            final float velocityZ = speed * inputVector.getY();
            final float sin = MathHelper.sin(rotationYaw * 0.017453292519943295);
            final float cos = MathHelper.cos(rotationYaw * 0.017453292519943295);
            offset = new DoubleVector3(velocityX * cos - velocityZ * sin, offset.getY(), velocityX * cos + velocityZ * sin);
            magnitude = (float)offset.lengthSquared();
            if (magnitude <= 0.001f) {
                return false;
            }
        }
        final float inverseMagnitude = MathHelper.fastInverseSqrt(magnitude);
        final DoubleVector3 scaledOffset = offset.copy().multiply(magnitude);
        final float horizontalDistance = (float)offset.horizontalDistanceSquared();
        if (horizontalDistance >= -0.15f) {
            final BlockState blockState = this.level.getBlockState(new DoubleVector3(position.getX(), position.getY() + this.behavior.boundingBox().getMaxY(), position.getZ()));
            if (blockState.hasCollision()) {
                return false;
            }
            final IntVector3 blockPosition = blockState.position();
            blockPosition.add(0, 1, 0);
            final BlockState aboveBlockState = this.level.getBlockState(blockPosition);
            if (aboveBlockState.hasCollision()) {
                return false;
            }
            final float speedLimit = Math.max(speed * 7.0f, 1.0f / inverseMagnitude);
            final DoubleVector3 jumpSpeedLimit = positionWithOffset.copy().add(scaledOffset.multiply(speedLimit));
            final AxisAlignedBoundingBox behaviorBoundingBox = this.behavior.boundingBox();
            final double xWidth = behaviorBoundingBox.getXWidth();
            final double zWidth = behaviorBoundingBox.getZWidth();
            final double height = behaviorBoundingBox.getHeight();
            final AxisAlignedBoundingBox boundingBox = new AxisAlignedBoundingBox(position, jumpSpeedLimit.add(0.0, height, 0.0)).inflate(xWidth, 0.0, zWidth);
            final List<AxisAlignedBoundingBox> blockCollisions = this.level.getBlockCollisions(boundingBox);
            double y = Double.MIN_VALUE;
            for (final AxisAlignedBoundingBox blockCollision : blockCollisions) {
                if (boundingBox.intersects(blockCollision)) {
                    y = blockCollision.getMaxY();
                    final DoubleVector3 center = blockCollision.getCenter();
                    final IntVector3 blockCollisionPosition = new IntVector3(center);
                    for (int index = 1; index < 1.2f; ++index) {
                        final IntVector3 indexPosition = new IntVector3(blockCollisionPosition.getX(), blockCollisionPosition.getY() + index, blockCollisionPosition.getZ());
                        final BlockState above = this.level.getBlockState(indexPosition);
                        if (above.hasCollision()) {
                            y = blockCollision.getMaxY() + blockCollisionPosition.getY();
                            if (y - this.behavior.position().getY() > 1.2000000476837158) {
                                return false;
                            }
                        }
                        if (index > 1) {
                            blockCollisionPosition.add(0, 1, 0);
                            final BlockState currentBlockState = this.level.getBlockState(blockCollisionPosition);
                            if (currentBlockState.hasCollision()) {
                                return false;
                            }
                        }
                    }
                    break;
                }
            }
            if (y != 1.401298464324817E-45) {
                final double jumpOffset = y - this.behavior.position().getY();
                if (jumpOffset > 0.5 && jumpOffset <= 1.2000000476837158) {
                    this.jumpTrigger = 7;
                    return true;
                }
            }
        }
        return false;
    }
}
