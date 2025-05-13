// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity;

import net.labymod.api.volt.annotation.RenamedFrom;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.position.Position;

public interface Entity
{
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosX() {
        return (float)this.position().getX();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosX() {
        return (float)this.previousPosition().getX();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosY() {
        return (float)this.position().getY();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosY() {
        return (float)this.previousPosition().getY();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosZ() {
        return (float)this.position().getZ();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosZ() {
        return (float)this.previousPosition().getZ();
    }
    
    Position position();
    
    Position previousPosition();
    
    boolean isCrouching();
    
    boolean isInvisible();
    
    boolean isInvisibleFor(final Player p0);
    
    boolean isSprinting();
    
    UUID getUniqueId();
    
    AxisAlignedBoundingBox axisAlignedBoundingBox();
    
    FloatVector3 perspectiveVector(final float p0);
    
    EntityPose entityPose();
    
    default boolean isCrouchingPose() {
        return this.entityPose() == EntityPose.CROUCHING;
    }
    
    boolean canEnterEntityPose(final EntityPose p0);
    
    float getEyeHeight();
    
    float getRotationYaw();
    
    void setRotationYaw(final float p0);
    
    float getPreviousRotationYaw();
    
    void setPreviousRotationYaw(final float p0);
    
    float getRotationPitch();
    
    void setRotationPitch(final float p0);
    
    float getPreviousRotationPitch();
    
    void setPreviousRotationPitch(final float p0);
    
    default double getDistanceSquared(final Entity entity) {
        return this.position().distanceSquared(entity.position());
    }
    
    default double getDistanceSquared(final FloatVector3 location) {
        return this.getDistanceSquared(location.getX(), location.getY(), location.getZ());
    }
    
    default double getDistanceSquared(final DoubleVector3 location) {
        return this.getDistanceSquared(location.getX(), location.getY(), location.getZ());
    }
    
    default double getDistanceSquared(final double x, final double y, final double z) {
        return this.position().distanceSquared(x, y, z);
    }
    
    DataWatcher dataWatcher();
    
    TagType nameTagType();
    
    void setNameTagType(final TagType p0);
    
    void setRendered(final boolean p0);
    
    boolean isRendered();
    
    boolean isInWater();
    
    boolean isInLava();
    
    boolean isUnderWater();
    
    boolean isOnFire();
    
    default boolean isInLiquid() {
        return this.isInWater() || this.isInLava();
    }
    
    boolean isOnGround();
    
    Entity getVehicle();
    
    ResourceLocation entityId();
    
    default float getForwardMotion() {
        final Position position = this.position();
        final Position previousPosition = this.previousPosition();
        final double motionX = previousPosition.getX() - position.getX();
        final double motionZ = previousPosition.getZ() - position.getZ();
        final float yawSin = MathHelper.sin(Math.toRadians(this.getRotationYaw()));
        final float yawCos = -MathHelper.cos(Math.toRadians(this.getRotationYaw()));
        return (float)(motionX * yawSin + motionZ * yawCos);
    }
    
    default FloatVector3 eyePosition() {
        return this.position().toFloatVector3().add(0.0f, this.getEyeHeight(), 0.0f);
    }
    
    @RenamedFrom("position()Lnet/labymod/api/util/math/vector/FloatVector3;")
    default FloatVector3 deprecated$position() {
        return this.position().toFloatVector3();
    }
}
