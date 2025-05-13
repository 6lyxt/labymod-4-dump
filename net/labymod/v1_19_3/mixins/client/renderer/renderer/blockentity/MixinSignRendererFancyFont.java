// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer.renderer.blockentity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_19_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fge.class })
public abstract class MixinSignRendererFancyFont
{
    @Inject(method = { "renderSignText" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;getDarkColor(Lnet/minecraft/world/level/block/entity/SignBlockEntity;)I", shift = At.Shift.AFTER) })
    private void labyMod$configureTextEdgeStrength(final cxp $$0, final eed $$1, final fdv $$2, final int $$3, final float $$4, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.0f);
    }
    
    @Inject(method = { "renderSignText" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE) })
    private void labyMod$setDefaultTextEdgeStrength(final cxp $$0, final eed $$1, final fdv $$2, final int $$3, final float $$4, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.5f);
    }
}
