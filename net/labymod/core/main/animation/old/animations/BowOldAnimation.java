// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.Laby;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class BowOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "bow";
    public static final boolean LEGACY_BLOCK_BUILD_THIRD_PERSON;
    private final PermissionRegistry permissionRegistry;
    
    public BowOldAnimation() {
        super("bow");
        this.permissionRegistry = Laby.references().permissionRegistry();
    }
    
    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(final HumanoidModelPoseAnimationEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        if (BowOldAnimation.LEGACY_BLOCK_BUILD_THIRD_PERSON) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        final LivingEntity.Hand handInUse = entity.getUsedItemHand();
        final ItemStack itemInUse = (handInUse == LivingEntity.Hand.MAIN_HAND) ? entity.getMainHandItemStack() : entity.getOffHandItemStack();
        if (!(entity instanceof Player) || !itemInUse.isBow() || entity.getItemUseDurationTicks() <= 0) {
            return;
        }
        event.setCancelled(true);
    }
    
    @Subscribe
    public void injectArmPoseThirdPerson(final HumanoidModelAnimateEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        if (BowOldAnimation.LEGACY_BLOCK_BUILD_THIRD_PERSON) {
            return;
        }
        final LivingEntity entity = event.livingEntity();
        final LivingEntity.Hand handInUse = entity.getUsedItemHand();
        final ItemStack itemInUse = (handInUse == LivingEntity.Hand.MAIN_HAND) ? entity.getMainHandItemStack() : entity.getOffHandItemStack();
        if (!(entity instanceof Player) || !itemInUse.isBow() || entity.getItemUseDurationTicks() <= 0) {
            return;
        }
        final boolean mainHandIsRight = entity.isMainHandRight();
        final boolean handInUseRight = mainHandIsRight ? (handInUse == LivingEntity.Hand.MAIN_HAND) : (handInUse == LivingEntity.Hand.OFF_HAND);
        final float mirror = handInUseRight ? 1.0f : -1.0f;
        final HumanoidModel model = event.model();
        final FloatVector3 head = model.getHead().getAnimationTransformation().getRotation();
        final ModelPart mainArm = handInUseRight ? model.getRightArm() : model.getLeftArm();
        final ModelPart supportiveArm = handInUseRight ? model.getLeftArm() : model.getRightArm();
        final FloatVector3 mainRot = mainArm.getAnimationTransformation().getRotation();
        mainRot.setY(-0.1f * mirror + head.getY());
        mainRot.setX(-1.5707964f + head.getX());
        final FloatVector3 secRot = supportiveArm.getAnimationTransformation().getRotation();
        secRot.setY(0.1f * mirror + head.getY() + 0.4f * mirror);
        secRot.setX(-1.5707964f + head.getX());
    }
    
    @Subscribe
    public void onRenderItemInHand(final RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER || !this.isEnabled()) {
            return;
        }
        final RenderFirstPersonItemInHandEvent.AnimationType type = event.animationType();
        if (type != RenderFirstPersonItemInHandEvent.AnimationType.BOW) {
            return;
        }
        final Stack stack = event.stack();
        if (event.isUsingItem()) {
            if (GeneralItemPostureOldAnimation.LEGACY_PVP) {
                stack.translate(-0.080625f, 0.11625f, -0.11125f);
                stack.rotate(0.08f, 1.0f, 0.0f, 0.0f);
                stack.rotate(0.97f, 0.0f, 1.0f, 0.0f);
                stack.rotate(0.16f, 0.0f, 0.0f, 1.0f);
            }
            else {
                stack.translate(-0.046875f, 0.0375f, -0.0125f);
                stack.rotate(2.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        else {
            GeneralItemPostureOldAnimation.apply(stack, event.hand());
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldBow());
    }
    
    static {
        LEGACY_BLOCK_BUILD_THIRD_PERSON = MinecraftVersions.V1_12_2.orOlder();
    }
}
