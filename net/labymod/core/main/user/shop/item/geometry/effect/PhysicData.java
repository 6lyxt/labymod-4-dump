// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect;

import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;

public class PhysicData
{
    private float forward;
    private float strafe;
    private float gravity;
    private float renderYawOffset;
    private float pitch;
    private int walkingPetIndex;
    
    public void update(final Player player, final PlayerModel playerModel, final float partialTicks) {
        final Position position = player.position();
        final Position previousPosition = player.previousPosition();
        final Position chasingPosition = player.chasingPosition();
        final Position previousChasingPosition = player.previousChasingPosition();
        final double motionX = chasingPosition.lerpX(previousChasingPosition, partialTicks) - position.lerpX(previousPosition, partialTicks);
        final double motionY = chasingPosition.lerpY(previousChasingPosition, partialTicks) - position.lerpY(previousPosition, partialTicks);
        final double motionZ = chasingPosition.lerpZ(previousChasingPosition, partialTicks) - position.lerpZ(previousPosition, partialTicks);
        final float cameraMotionYaw = MathHelper.lerp(player.getCameraYaw(), player.getPreviousCameraYaw(), partialTicks);
        float gravity = MathHelper.clamp((float)motionY * 10.0f, -6.0f, 32.0f);
        gravity += (float)(Math.sin(MathHelper.lerp(player.getWalkDistance(), player.getPreviousWalkDistance(), partialTicks) * 6.0f) * 32.0 * cameraMotionYaw);
        final float renderYawOffset = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        final double yawSin = Math.sin(MathHelper.toRadiansDouble(renderYawOffset));
        final double yawCos = -Math.cos(MathHelper.toRadiansDouble(renderYawOffset));
        float forward = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
        forward = MathHelper.clamp(forward, 0.0f, 150.0f);
        float strafe = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        strafe = MathHelper.clamp(strafe, -20.0f, 20.0f);
        final float pitch = MathHelper.toDegreesFloat(playerModel.getHead().getAnimationTransformation().getRotation().getX());
        this.forward = MathHelper.toRadiansFloat(forward / 2.0f);
        this.gravity = MathHelper.toRadiansFloat(gravity);
        this.strafe = MathHelper.toRadiansFloat(strafe / 2.0f);
        this.renderYawOffset = renderYawOffset;
        this.pitch = pitch;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public float getGravity() {
        return this.gravity;
    }
    
    public float getRenderYawOffset() {
        return this.renderYawOffset;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void resetWalkingPetIndex() {
        this.walkingPetIndex = 0;
    }
    
    public int getAndIncrementWalkingPetIndex() {
        return this.walkingPetIndex++;
    }
}
