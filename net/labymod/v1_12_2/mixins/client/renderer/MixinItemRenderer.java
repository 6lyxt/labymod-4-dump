// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.DynamicStackProvider;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ buu.class })
public abstract class MixinItemRenderer
{
    private RenderFirstPersonItemInHandEvent event;
    @Unique
    private final Stack labymod$stack;
    @Unique
    private FloatVector3 labymod$offset;
    
    public MixinItemRenderer() {
        this.labymod$stack = Stack.create(new DynamicStackProvider());
        this.labymod$offset = FloatVector3.ZERO;
    }
    
    @Inject(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = { @At("HEAD") })
    private void labymod$renderArmWithItemPre(final bua player, final float partialTicks, final float yaw, final ub hand, final float attackProgress, final aip itemStack, final float equipProgress, final CallbackInfo callbackInfo) {
        final boolean isMainHand = hand == ub.a;
        final vo humanoidArm = isMainHand ? player.cF() : player.cF().a();
        final boolean isRightArm = humanoidArm == vo.b;
        final boolean isUsingItem = player.cG() && player.cK() > 0 && player.cH() == hand;
        final Player apiPlayer = (Player)player;
        this.event = new RenderFirstPersonItemInHandEvent(VersionedStackProvider.DEFAULT_STACK, apiPlayer, MinecraftUtil.obtainPlayerModel(apiPlayer), isMainHand ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND, isRightArm ? LivingEntity.HandSide.RIGHT : LivingEntity.HandSide.LEFT, MinecraftUtil.fromMinecraft(itemStack), RenderFirstPersonItemInHandEvent.AnimationType.fromMinecraft(itemStack.n()), partialTicks, equipProgress, attackProgress, isUsingItem);
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.HEAD);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformSideFirstPerson(Lnet/minecraft/util/EnumHandSide;F)V"))
    private void labymod$transformSideFirstPersonPre(final buu itemRenderer, final vo arm, final float equipProgress) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ARM_TRANSFORM);
        if (this.event.isApplyItemArmTransform()) {
            this.b(arm, this.event.getEquipProgress());
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ARM_TRANSFORM);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPerson(Lnet/minecraft/util/EnumHandSide;F)V"))
    private void labymod$transformFirstPersonPre(final buu itemRenderer, final vo arm, final float attackProgress) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_ATTACK_TRANSFORM);
        if (this.event.isApplyItemArmAttackTransform()) {
            this.a(arm, this.event.isAttackWhileItemUse() ? this.event.getAttackProgress() : attackProgress);
        }
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.POST_ATTACK_TRANSFORM);
    }
    
    @Redirect(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"))
    private void labymod$renderItemSidePreRender(final buu itemRenderer, final vp entity, final aip itemStack, final bwc.b type, final boolean isLeft) {
        this.fireEvent(RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER);
        if (this.event == null || this.event.isRenderItem()) {
            itemRenderer.a(entity, itemStack, type, isLeft);
        }
    }
    
    @Inject(method = { "renderOverlays(F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$getViewBlockingStateHeadSetup(final float partialTicks, final CallbackInfo ci) {
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f, false);
        this.labymod$stack.pop();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posX:D"))
    private double labyMod$getViewBlockingStateX(final aed instance) {
        return instance.p - this.labymod$offset.getX();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posY:D"))
    private double labyMod$getViewBlockingStateY(final aed instance) {
        return instance.q - this.labymod$offset.getY();
    }
    
    @Redirect(method = { "renderOverlays(F)V" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;posZ:D"))
    private double labyMod$getViewBlockingStateZ(final aed instance) {
        return instance.r - this.labymod$offset.getZ();
    }
    
    @Shadow
    protected abstract void b(final vo p0, final float p1);
    
    @Shadow
    protected abstract void a(final vo p0, final float p1);
    
    private void fireEvent(final RenderFirstPersonItemInHandEvent.TransformPhase phase) {
        if (this.event != null) {
            this.event.setPhase(phase);
            Laby.fireEvent(this.event);
        }
    }
}
