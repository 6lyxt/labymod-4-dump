// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations.legacy;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class LegacySneakingOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "legacy_sneaking";
    public static final boolean IS_LEGACY_VERSION;
    public static final float LEGACY_CROUCH_OFFSET = 0.2f;
    public static final float LEGACY_CROUCH_SPEED = 0.4f;
    private final IntaveProtocol intaveProtocol;
    private float legacyOffset;
    private float prevLegacyOffset;
    
    public LegacySneakingOldAnimation() {
        super("legacy_sneaking");
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }
    
    public static float getLegacyCrouchOffset(final Entity entity) {
        return entity.isCrouching() ? 0.080000006f : 0.0f;
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
        this.prevLegacyOffset = this.legacyOffset;
        if (cameraEntity.isCrouching() && this.legacyOffset < 0.2f) {
            this.legacyOffset = 0.2f;
        }
        this.legacyOffset *= 0.4f;
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
        final float vanillaCrouchOffset = getLegacyCrouchOffset(cameraEntity);
        final float legacyCrouchOffset = MathHelper.lerp(this.legacyOffset, this.prevLegacyOffset, event.getPartialTicks());
        event.setEyeHeight(event.getEyeHeight() + vanillaCrouchOffset - legacyCrouchOffset);
    }
    
    @Override
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(LegacySneakingOldAnimation.IS_LEGACY_VERSION && this.permissionRegistry.isPermissionEnabled("sneaking_visual", this.classicPvPConfig.oldSneaking()));
    }
    
    static {
        IS_LEGACY_VERSION = MinecraftVersions.V1_12_2.orOlder();
    }
}
