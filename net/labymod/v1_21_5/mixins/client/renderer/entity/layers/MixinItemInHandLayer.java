// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.entity.LivingEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_5.client.renderer.ItemStackRenderStateAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ hcb.class })
public class MixinItemInHandLayer
{
    private Stack labyMod$stack;
    private byf labyMod$livingEntity;
    private dak labyMod$itemStack;
    
    @Inject(method = { "renderArmWithItem" }, at = { @At("HEAD") })
    private void labyMod$storeEntity(final hdg armedEntityRenderState, final hhi itemStackRenderState, final bxw humanoidArm, final fld poseStack, final grn bufferSource, final int packedLightCoords, final CallbackInfo ci) {
        final EntityRenderStateAccessor<byf> entityState = EntityRenderStateAccessor.self(armedEntityRenderState);
        if (entityState == null) {
            this.labyMod$livingEntity = null;
            this.labyMod$itemStack = null;
            return;
        }
        this.labyMod$livingEntity = entityState.labyMod$getEntity();
        this.labyMod$itemStack = this.labyMod$livingEntity.a(humanoidArm);
    }
    
    @WrapOperation(method = { "renderArmWithItem" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V") })
    private void labyMod$callItemInHandLayerRenderEvent(final hhi renderState, final fld poseStack, final grn bufferSource, final int packedLightCoords, final int packedOverlayCoords, final Operation<Void> original) {
        if (this.labyMod$livingEntity == null) {
            return;
        }
        final ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor)renderState;
        final boolean leftHand = accessor.labyMod$isLeftHand();
        final dai displayContext = accessor.labyMod$getItemDisplayContext();
        this.labyMod$stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final ItemInHandLayerRenderEvent event = this.labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, this.labyMod$livingEntity, this.labyMod$itemStack, displayContext, leftHand, packedLightCoords);
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[] { renderState, poseStack, bufferSource, packedLightCoords, packedOverlayCoords });
        this.labyMod$fireItemInHandLayerRenderEvent(Phase.POST, this.labyMod$livingEntity, this.labyMod$itemStack, displayContext, leftHand, packedLightCoords);
    }
    
    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(final Phase phase, final byf livingEntity, final dak itemStack, final dai type, final boolean leftHand, final int combinedLight) {
        return Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity)livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
