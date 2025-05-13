// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.renderer;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fjt.class })
public abstract class MixinItemInHandRenderer
{
    private RenderFirstPersonItemInHandEvent event;
    
    @Inject(method = { "renderArmWithItem" }, at = { @At("HEAD") })
    private void labymod$renderArmWithItemPre(final fiv player, final float partialTicks, final float yaw, final bdw hand, final float attackProgress, final cfz itemStack, final float equipProgress, final eij poseStack, final fjx source, final int packedLight, final CallbackInfo callbackInfo) {
        final boolean isMainHand = hand == bdw.a;
        final bft humanoidArm = isMainHand ? player.fh() : player.fh().d();
        final boolean isRightArm = humanoidArm == bft.b;
        final boolean isUsingItem = player.fi() && player.fl() > 0 && player.fj() == hand;
        final Player apiPlayer = (Player)player;
        this.event = new RenderFirstPersonItemInHandEvent(((VanillaStackAccessor)poseStack).stack(source), apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.s()), partialTicks, equipProgress, attackProgress, isUsingItem);
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }
    
    @Redirect(method = { "renderArmWithItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    private void labymod$applyItemArmTransformPre(final fjt renderer, final eij poseStack, final bft arm, final float equipProgress) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            this.b(poseStack, arm, this.event.getEquipProgress());
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
        if (this.event.isUsingItem() && this.event.isApplyItemArmAttackTransform() && this.event.isAttackWhileItemUse()) {
            final RenderFirstPersonItemInHandEvent.AnimationType type = this.event.animationType();
            if (type == RenderFirstPersonItemInHandEvent.AnimationType.BOW || type == RenderFirstPersonItemInHandEvent.AnimationType.EAT || type == RenderFirstPersonItemInHandEvent.AnimationType.DRINK) {
                this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
                this.a(poseStack, arm, this.event.getAttackProgress());
                this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
            }
        }
    }
    
    @Redirect(method = { "renderArmWithItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    private void labymod$applyItemArmAttackTransformPre(final fjt renderer, final eij poseStack, final bft arm, final float attackProgress) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            this.a(poseStack, arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }
    
    @Redirect(method = { "renderArmWithItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void labymod$renderItemPreRender(final fjt instance, final bfz livingEntity, final cfz itemStack, final cfw itemDisplayContext, final boolean isLeft, final eij poseStack, final fjx multiBufferSource, final int packedLight) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            instance.a(livingEntity, itemStack, itemDisplayContext, isLeft, poseStack, multiBufferSource, packedLight);
        }
    }
    
    @Shadow
    protected abstract void b(final eij p0, final bft p1, final float p2);
    
    @Shadow
    protected abstract void a(final eij p0, final bft p1, final float p2);
    
    private void fireEvent(final RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}
