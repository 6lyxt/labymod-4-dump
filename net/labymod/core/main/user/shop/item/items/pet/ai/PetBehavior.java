// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai;

import net.labymod.api.util.math.position.Position;
import java.util.Random;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.items.pet.ai.controller.LookController;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;

public interface PetBehavior
{
    void tick();
    
    default void setPosition(final FloatVector3 position) {
        this.setPosition(position.getX(), position.getY(), position.getZ());
    }
    
    default void setPosition(final DoubleVector3 position) {
        this.setPosition(position.getX(), position.getY(), position.getZ());
    }
    
    void setPosition(final double p0, final double p1, final double p2);
    
    default void addDeltaMovement(final FloatVector3 movement) {
        this.addDeltaMovement(movement.getX(), movement.getY(), movement.getZ());
    }
    
    default void addDeltaMovement(final DoubleVector3 movement) {
        this.addDeltaMovement((float)movement.getX(), (float)movement.getY(), (float)movement.getZ());
    }
    
    void addDeltaMovement(final float p0, final float p1, final float p2);
    
    default void setDeltaMovement(final FloatVector3 movement) {
        this.setDeltaMovement(movement.getX(), movement.getY(), movement.getZ());
    }
    
    void setDeltaMovement(final float p0, final float p1, final float p2);
    
    FloatVector3 deltaMovement();
    
    default DoubleVector3 position() {
        return this.transform().position();
    }
    
    default DoubleVector3 previousPosition() {
        return this.transform().previousPosition();
    }
    
    Transform transform();
    
    LookController lookController();
    
    Model petModel();
    
    AxisAlignedBoundingBox boundingBox();
    
    boolean onGround();
    
    void setOnGround(final boolean p0);
    
    void move(final FloatVector3 p0);
    
    default WalkType findWalkType(final Player player) {
        if (player.abilities().flying().get()) {
            return WalkType.FLYING;
        }
        if (player.isSprinting()) {
            return WalkType.SPRINT;
        }
        return WalkType.WALK;
    }
    
    default DoubleVector3 findRandomPointOfInterest(final Random random) {
        final DoubleVector3 currentPosition = this.position();
        final double x = currentPosition.getX() + (random.nextFloat() * 10.0f - 5.0f);
        final double y = currentPosition.getY();
        final double z = currentPosition.getZ() + (random.nextFloat() * 10.0f - 5.0f);
        return new DoubleVector3(x, y, z);
    }
    
    float getMovementSpeed();
    
    default float getStepHeight() {
        return 0.5f;
    }
    
    boolean isWalking();
    
    void teleportTo(final double p0, final double p1, final double p2);
    
    default void teleportTo(final Player player) {
        final Position position = player.position();
        this.teleportTo(position.getX(), position.getY(), position.getZ());
    }
    
    int getPetIndex();
    
    int getMaxPets();
    
    void updatePetIndex(final int p0, final int p1);
}
