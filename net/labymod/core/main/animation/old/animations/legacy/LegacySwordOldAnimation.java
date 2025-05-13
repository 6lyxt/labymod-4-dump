// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations.legacy;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.animation.old.animations.SwordOldAnimation;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.core.main.animation.old.BlockingSwordAccessor;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class LegacySwordOldAnimation extends AbstractOldAnimation implements BlockingSwordAccessor
{
    public static final String NAME = "legacy_sword";
    public static final boolean LEGACY_PVP;
    
    public LegacySwordOldAnimation() {
        super("legacy_sword");
    }
    
    @Subscribe
    public void injectBlockAnimationInFirstPerson(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER) {
            return;
        }
        if (!this.isBlockingWithSword(event.player())) {
            return;
        }
        final Stack stack = event.stack();
        stack.translate(-0.02f, 0.02f, 0.15f);
    }
    
    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(final HumanoidModelPoseAnimationEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (entity instanceof final Player player) {
            if (event.handSide() == LivingEntity.HandSide.RIGHT && this.isBlockingWithSword(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    @Subscribe
    public void injectArmPoseThirdPerson(final HumanoidModelAnimateEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (!this.isBlockingWithSword(player)) {
            return;
        }
        final ModelPart rightArm = event.model().getRightArm();
        final FloatVector3 rotation = rightArm.getAnimationTransformation().getRotation();
        rotation.add(SwordOldAnimation.ARM_ROTATION_BLOCKING);
    }
    
    @Subscribe
    public void modifyItemsInHandInThirdPerson(final ItemInHandLayerRenderEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        final Player player = (Player)entity;
        if (!this.isBlockingWithSword(player)) {
            return;
        }
        final Stack stack = event.stack();
        stack.rotate(70.0f, 0.0f, 0.0f, -1.0f);
        stack.rotate(35.0f, 0.0f, -1.0f, 0.0f);
        stack.rotate(20.0f, 1.0f, 0.0f, 0.0f);
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldSword()) && LegacySwordOldAnimation.LEGACY_PVP;
    }
    
    @Override
    public boolean isBlockingWithSword(final Player player) {
        final ItemStack itemStack = player.getMainHandItemStack();
        return itemStack.isSword() && player.getItemUseDurationTicks() > 0;
    }
    
    static {
        LEGACY_PVP = MinecraftVersions.V1_8_9.orOlder();
    }
}
