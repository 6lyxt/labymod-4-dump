// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.core.event.client.render.entity.EntityEyeHeightEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.main.animation.old.animations.legacy.LegacySneakingOldAnimation;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class SneakingOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "sneaking";
    private static final float MODERN_CROUCH_OFFSET = 0.35f;
    private static final float MODERN_CROUCH_SPEED = 0.5f;
    private final IntaveProtocol intaveProtocol;
    private float legacyOffset;
    private float prevLegacyOffset;
    private float modernOffset;
    private float prevModernOffset;
    
    public SneakingOldAnimation() {
        super("sneaking");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }
    
    public static float getModernCrouchOffset(final Entity entity) {
        return entity.isCrouchingPose() ? 0.35f : 0.0f;
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final Entity cameraEntity = Laby.labyAPI().minecraft().getCameraEntity();
        if (cameraEntity == null || Laby.labyAPI().minecraft().isPaused()) {
            return;
        }
        final boolean stuckUnderBlock = !cameraEntity.canEnterEntityPose(EntityPose.STANDING);
        final boolean isSwimming = cameraEntity.entityPose() == EntityPose.SWIMMING;
        this.prevLegacyOffset = this.legacyOffset;
        if (stuckUnderBlock && !isSwimming) {
            this.legacyOffset = 0.35f;
        }
        else {
            if (cameraEntity.isCrouching() && this.legacyOffset < 0.2f) {
                this.legacyOffset = 0.2f;
            }
            this.legacyOffset *= 0.4f;
        }
        this.prevModernOffset = this.modernOffset;
        final float crouchOffset = (this.isActualPermissionEnabled() && !stuckUnderBlock) ? LegacySneakingOldAnimation.getLegacyCrouchOffset(cameraEntity) : getModernCrouchOffset(cameraEntity);
        this.modernOffset += (crouchOffset - this.modernOffset) * 0.5f;
    }
    
    @Subscribe
    public void onCameraEyeHeight(final CameraEyeHeightEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        final Entity cameraEntity = Laby.labyAPI().minecraft().getCameraEntity();
        if (cameraEntity == null) {
            return;
        }
        final float partialTicks = event.getPartialTicks();
        final float vanillaCrouchOffset = MathHelper.lerp(this.modernOffset, this.prevModernOffset, partialTicks);
        final float legacyCrouchOffset = MathHelper.lerp(this.legacyOffset, this.prevLegacyOffset, partialTicks);
        event.setEyeHeight(event.getEyeHeight() + vanillaCrouchOffset - legacyCrouchOffset);
    }
    
    @Subscribe
    public void onEntityEyeHeight(final EntityEyeHeightEvent event) {
        if (!this.isEnabled() || !this.isActualPermissionEnabled()) {
            return;
        }
        final Entity cameraEntity = Laby.labyAPI().minecraft().getCameraEntity();
        if (cameraEntity == null || event.entity() != cameraEntity) {
            return;
        }
        final boolean stuckUnderBlock = !cameraEntity.canEnterEntityPose(EntityPose.STANDING);
        if (stuckUnderBlock) {
            return;
        }
        final float vanillaCrouchOffset = getModernCrouchOffset(cameraEntity);
        final float legacyCrouchOffset = LegacySneakingOldAnimation.getLegacyCrouchOffset(cameraEntity);
        event.setEyeHeight(event.getEyeHeight() + vanillaCrouchOffset - legacyCrouchOffset);
    }
    
    public boolean isActualPermissionEnabled() {
        return this.permissionRegistry.isPermissionEnabled("sneaking");
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(!LegacySneakingOldAnimation.IS_LEGACY_VERSION && this.permissionRegistry.isPermissionEnabled("sneaking", this.classicPvPConfig.oldSneaking()));
    }
}
