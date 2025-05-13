// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.renderer.blockentity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eta.class })
public abstract class MixinSignRendererFancyFont
{
    @Inject(method = { "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;getDarkColor(Lnet/minecraft/world/level/block/entity/SignBlockEntity;)I", shift = At.Shift.AFTER) })
    private void labyMod$configureTextEdgeStrength(final cnr entity, final float param1, final dtm param2, final eqs param3, final int param4, final int param5, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.0f);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE, ordinal = 1) })
    private void labyMod$setDefaultTextEdgeStrength(final cnr entity, final float param1, final dtm param2, final eqs param3, final int param4, final int param5, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.5f);
    }
}
