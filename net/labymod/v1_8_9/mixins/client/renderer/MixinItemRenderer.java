// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.DynamicStackProvider;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfn.class })
public abstract class MixinItemRenderer
{
    @Shadow
    @Final
    private ave c;
    @Shadow
    private zx d;
    @Shadow
    private float e;
    @Shadow
    private float f;
    private RenderFirstPersonItemInHandEvent event;
    @Unique
    private final Stack labymod$stack;
    @Unique
    private FloatVector3 labymod$offset;
    
    public MixinItemRenderer() {
        this.labymod$stack = Stack.create(new DynamicStackProvider());
        this.labymod$offset = FloatVector3.ZERO;
    }
    
    @Inject(method = { "renderPlayerArms" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderLeftArm(Lnet/minecraft/client/renderer/entity/RenderPlayer;)V", shift = At.Shift.BEFORE) })
    private void labyMod$rebindSkin(final bet player, final CallbackInfo ci) {
        this.c.P().a(player.i());
    }
    
    @Inject(method = { "renderItemInFirstPerson(F)V" }, at = { @At("HEAD") })
    private void labymod$renderItemInFirstPersonPre(final float partialTicks, final CallbackInfo callbackInfo) {
        if (this.d == null) {
            return;
        }
        final bet player = (bet)this.c.h;
        final float equipProgress = 1.0f - (this.f + (this.e - this.f) * partialTicks);
        final float swingProgress = player.l(partialTicks);
        final boolean isUsingItem = player.bR() > 0;
        final Player apiPlayer = (Player)player;
        this.event = new RenderFirstPersonItemInHandEvent(VersionedStackProvider.DEFAULT_STACK, apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), LivingEntity.Hand.MAIN_HAND, LivingEntity.HandSide.RIGHT, MinecraftUtil.fromMinecraft(this.d), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(this.d.m()), partialTicks, equipProgress, swingProgress, isUsingItem);
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getItemInUseCount()I"))
    private int labymod$getItemInUseCount(final bet player) {
        return this.event.isUsingItem() ? 1 : 0;
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doItemUsedTransformations(F)V"))
    private void labymod$doItemUsedTransformationsPre(final bfn itemRenderer, final float partialTicks) {
        if (this.d == null) {
            return;
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            this.d(this.event.getAttackProgress());
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"))
    private void labymod$transformFirstPersonItemPre(final bfn itemRenderer, final float equippedProgress, final float attackProgress) {
        if (this.d == null) {
            return;
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            this.b(this.event.getEquipProgress(), this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void labymod$renderItemPreRender(final bfn itemRenderer, final pr entity, final zx itemStack, final bgr.b type) {
        if (this.d == null) {
            return;
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            itemRenderer.a(entity, itemStack, type);
        }
    }
    
    @Inject(method = { "renderOverlays(F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$getViewBlockingStateHeadSetup(final float partialTicks, final CallbackInfo ci) {
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f, false);
        this.labymod$stack.pop();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D"))
    private double labyMod$getViewBlockingStateX(final wn instance) {
        return instance.s - this.labymod$offset.getX();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D"))
    private double labyMod$getViewBlockingStateY(final wn instance) {
        return instance.t - this.labymod$offset.getY();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D"))
    private double labyMod$getViewBlockingStateZ(final wn instance) {
        return instance.u - this.labymod$offset.getZ();
    }
    
    @Shadow
    protected abstract void d(final float p0);
    
    @Shadow
    protected abstract void b(final float p0, final float p1);
    
    private void fireEvent(final RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}
