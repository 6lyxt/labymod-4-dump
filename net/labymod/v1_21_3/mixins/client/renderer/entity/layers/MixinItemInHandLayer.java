// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gvx.class })
public class MixinItemInHandLayer
{
    private Stack labyMod$stack;
    private bwg labyMod$livingEntity;
    
    @Inject(method = { "renderArmWithItem" }, at = { @At("HEAD") })
    private void labyMod$storeEntity(final gyt $$0, final hdn $$1, final cxp $$2, final cxn $$3, final bwa $$4, final fgs $$5, final gll $$6, final int $$7, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bwg> entityState = EntityRenderStateAccessor.self($$0);
        if (entityState == null) {
            this.labyMod$livingEntity = null;
            return;
        }
        this.labyMod$livingEntity = entityState.labyMod$getEntity();
    }
    
    @Redirect(method = { "renderArmWithItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"))
    private void labyMod$callItemInHandLayerRenderEvent(final gso renderer, final cxp itemStack, final cxn displayContext, final boolean leftHand, final fgs poseStack, final gll bufferSource, final int packedLightCoords, final int packedOverlayCoords, final hdn bakedModel) {
        if (this.labyMod$livingEntity == null) {
            return;
        }
        this.labyMod$stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final ItemInHandLayerRenderEvent event = this.labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, this.labyMod$livingEntity, itemStack, displayContext, leftHand, packedLightCoords);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(itemStack, displayContext, leftHand, poseStack, bufferSource, packedLightCoords, packedOverlayCoords, bakedModel);
        this.labyMod$fireItemInHandLayerRenderEvent(Phase.POST, this.labyMod$livingEntity, itemStack, displayContext, leftHand, packedLightCoords);
    }
    
    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(final Phase phase, final bwg livingEntity, final cxp itemStack, final cxn type, final boolean leftHand, final int combinedLight) {
        return Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity)livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
