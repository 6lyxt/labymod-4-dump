// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gnd.class })
public class MixinItemInHandLayer
{
    private Stack labyMod$stack;
    
    @Redirect(method = { "renderArmWithItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void labyMod$callItemInHandLayerRenderEvent(final gdl renderer, final btq livingEntity, final cuq itemStack, final cun type, final boolean leftHand, final ezz poseStack, final gdp bufferSource, final int combinedLight) {
        this.labyMod$stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final ItemInHandLayerRenderEvent event = this.labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, livingEntity, itemStack, type, leftHand, combinedLight);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type, leftHand, poseStack, bufferSource, combinedLight);
        this.labyMod$fireItemInHandLayerRenderEvent(Phase.POST, livingEntity, itemStack, type, leftHand, combinedLight);
    }
    
    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(final Phase phase, final btq livingEntity, final cuq itemStack, final cun type, final boolean leftHand, final int combinedLight) {
        return Laby.fireEvent(new ItemInHandLayerRenderEvent(this.labyMod$stack, phase, (LivingEntity)livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
