// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai.controller;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.MathHelper;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.Axis;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.api.util.math.vector.FloatVector2;

public class MovementController
{
    private static final float BASE_JUMP_POWER = 0.22f;
    private static final float GRAVITY = 0.08f;
    private final FloatVector2 input;
    private final PetBehavior behavior;
    private final ClientWorld level;
    private final JumpController jumpController;
    private final LookController lookController;
    private int insideFluidTicks;
    private int fakeJumpTicks;
    private float yVelocity;
    
    public MovementController(final PetBehavior behavior) {
        this.input = new FloatVector2();
        this.insideFluidTicks = 0;
        this.fakeJumpTicks = 0;
        this.behavior = behavior;
        this.level = Laby.labyAPI().minecraft().clientWorld();
        this.jumpController = new JumpController(behavior, this.level);
        this.lookController = new LookController(behavior);
    }
    
    public FloatVector3 move(final Player owner, final FloatVector3 deltaMovement) {
        this.input.set(this.getImpulse(deltaMovement.getX()), this.getImpulse(deltaMovement.getZ()));
        final boolean canJump = this.jumpController.canJump(this.input, deltaMovement.getX(), deltaMovement.getZ(), this.behavior.transform().rotation().getY());
        final boolean isFakeJumping = this.fakeJumpTicks > 0;
        if (canJump || isFakeJumping) {
            deltaMovement.set(deltaMovement.getX(), 0.22f, deltaMovement.getZ());
            if (!isFakeJumping) {
                this.fakeJumpTicks = 6;
            }
            this.behavior.setOnGround(false);
        }
        if (this.behavior.onGround()) {
            this.yVelocity = 0.0f;
        }
        else if (this.fakeJumpTicks == 0) {
            this.yVelocity += ((this.insideFluidTicks <= 0) ? 0.02f : 0.08f);
            deltaMovement.add(0.0f, -this.yVelocity, 0.0f);
            deltaMovement.multiply(0.6f, 0.98f, 0.6f);
        }
        this.moveWithCollisions(deltaMovement);
        this.handleFluidMovement(deltaMovement);
        return deltaMovement;
    }
    
    private float getImpulse(final double value) {
        if (value == 0.0) {
            return (float)value;
        }
        return (value < 0.0) ? -1.0f : 1.0f;
    }
    
    private boolean moveWithCollisions(final FloatVector3 deltaMovement) {
        final double prevX;
        double x = prevX = deltaMovement.getX();
        final double prevY;
        double y = prevY = deltaMovement.getY();
        final double prevZ;
        double z = prevZ = deltaMovement.getZ();
        final DoubleVector3 currentPosition = this.behavior.position();
        final AxisAlignedBoundingBox boundingBox = this.behavior.boundingBox().move(currentPosition);
        final List<AxisAlignedBoundingBox> blockCollisions = this.level.getBlockCollisions(boundingBox.expandTowards(deltaMovement));
        for (final AxisAlignedBoundingBox collision : blockCollisions) {
            if (y != 0.0) {
                y = collision.collide(Axis.Y, boundingBox, y);
            }
            final boolean firstZ = Math.abs(x) < Math.abs(z);
            if (firstZ && z != 0.0) {
                z = collision.collide(Axis.Z, boundingBox, z);
            }
            if (x != 0.0) {
                x = collision.collide(Axis.X, boundingBox, x);
            }
            if (!firstZ && z != 0.0) {
                z = collision.collide(Axis.Z, boundingBox, z);
            }
        }
        final boolean horizontalX = prevX != x;
        final boolean horizontalZ = prevZ != z;
        this.behavior.setOnGround(prevY != y && prevY < 0.0);
        if (prevY != y) {
            deltaMovement.setY((float)y);
        }
        if (horizontalX) {
            this.canStep(deltaMovement, currentPosition, blockCollisions, delta -> delta.setX(0.0f));
        }
        if (horizontalZ) {
            this.canStep(deltaMovement, currentPosition, blockCollisions, delta -> delta.setZ(0.0f));
        }
        return horizontalX || horizontalZ;
    }
    
    private void canStep(final FloatVector3 deltaMovement, final DoubleVector3 currentPosition, final List<AxisAlignedBoundingBox> blockCollisions, final Consumer<FloatVector3> collidedConsumer) {
        float max = 0.0f;
        for (final AxisAlignedBoundingBox blockCollision : blockCollisions) {
            final DoubleVector3 center = blockCollision.getCenter();
            final BlockState blockState = this.level.getBlockState(center);
            final int blockY = MathHelper.floor(currentPosition.getY());
            if (blockState.position().getY() < blockY) {
                continue;
            }
            final double maxY = blockCollision.getMaxY() - blockState.position().getY();
            final double diff = blockState.position().getY() + maxY - currentPosition.getY();
            if (diff > 0.625) {
                collidedConsumer.accept(deltaMovement);
            }
            else if (this.behavior.onGround()) {
                deltaMovement.set(0.0f, max = (float)Math.max(diff, max), 0.0f);
            }
            else {
                collidedConsumer.accept(deltaMovement);
            }
        }
    }
    
    public void tick() {
        if (this.insideFluidTicks > 0) {
            --this.insideFluidTicks;
        }
        if (this.fakeJumpTicks > 0) {
            --this.fakeJumpTicks;
        }
        this.jumpController.tick();
        this.lookController.tick();
    }
    
    private void handleFluidMovement(final FloatVector3 deltaMovement) {
        final BlockState blockState = this.level.getBlockState(this.behavior.position());
        if (blockState.isFluid()) {
            if (this.insideFluidTicks <= 0) {
                deltaMovement.add(0.0f, 0.2f, 0.0f);
            }
            deltaMovement.multiply(0.8f, 0.8f, 0.8f);
            this.behavior.setOnGround(false);
            this.resetGravityVelocity();
        }
        else {
            this.insideFluidTicks = 10;
        }
    }
    
    public void resetGravityVelocity() {
        this.yVelocity = 0.0f;
    }
    
    public LookController lookController() {
        return this.lookController;
    }
}
