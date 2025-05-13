// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ grg.class })
public abstract class MixinItemInHandRenderer
{
    private RenderFirstPersonItemInHandEvent event;
    
    @Inject(method = { "renderArmWithItem" }, at = { @At("HEAD") })
    private void labymod$renderArmWithItemPre(final gqj player, final float partialTicks, final float yaw, final bvb hand, final float attackProgress, final dak itemStack, final float equipProgress, final fld poseStack, final grn source, final int packedLight, final CallbackInfo callbackInfo) {
        final boolean isMainHand = hand == bvb.a;
        final bxw humanoidArm = isMainHand ? player.fy() : player.fy().e();
        final boolean isRightArm = humanoidArm == bxw.b;
        final boolean isUsingItem = player.fz() && player.fC() > 0 && player.fA() == hand;
        final Player apiPlayer = (Player)player;
        this.event = new RenderFirstPersonItemInHandEvent(((VanillaStackAccessor)poseStack).stack(source), apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.w()), partialTicks, equipProgress, attackProgress, isUsingItem);
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }
    
    @WrapOperation(method = { "renderArmWithItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V") })
    private void labymod$applyItemArmTransformPre(final grg instance, final fld poseStack, final bxw arm, final float equipProgress, final Operation<Void> original) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            this.b(poseStack, arm, this.event.getEquipProgress());
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
        if (this.event.isUsingItem() && this.event.isApplyItemArmAttackTransform() && this.event.isAttackWhileItemUse()) {
            final RenderFirstPersonItemInHandEvent.AnimationType type = this.event.animationType();
            if (type == RenderFirstPersonItemInHandEvent.AnimationType.BOW || type == RenderFirstPersonItemInHandEvent.AnimationType.EAT || type == RenderFirstPersonItemInHandEvent.AnimationType.DRINK) {
                this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
                original.call(new Object[] { instance, poseStack, arm, this.event.getAttackProgress() });
                this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
            }
        }
    }
    
    @WrapOperation(method = { "swingArm" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V") })
    private void labymod$applyItemArmAttackTransformPre(final grg instance, final fld poseStack, final bxw arm, final float attackProgress, final Operation<Void> original) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            original.call(new Object[] { instance, poseStack, arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress });
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }
    
    @WrapOperation(method = { "renderArmWithItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V") })
    private void labymod$renderItemPreRender(final grg instance, final byf livingEntity, final dak itemStack, final dai itemDisplayContext, final fld poseStack, final grn bufferSource, final int packedLightCoords, final Operation<Void> original) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            original.call(new Object[] { instance, livingEntity, itemStack, itemDisplayContext, poseStack, bufferSource, packedLightCoords });
        }
    }
    
    @Shadow
    protected abstract void b(final fld p0, final bxw p1, final float p2);
    
    private void fireEvent(final RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}
