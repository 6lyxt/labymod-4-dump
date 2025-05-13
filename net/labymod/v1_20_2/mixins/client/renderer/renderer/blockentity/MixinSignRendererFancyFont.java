// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.renderer.renderer.blockentity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqs.class })
public abstract class MixinSignRendererFancyFont
{
    @Inject(method = { "renderSignText" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;getDarkColor(Lnet/minecraft/world/level/block/entity/SignText;)I", shift = At.Shift.AFTER) })
    private void labyMod$configureTextEdgeStrength(final gw $$0, final dee $$1, final elp $$2, final foe $$3, final int $$4, final int $$5, final int $$6, final boolean $$7, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.0f);
    }
    
    @Inject(method = { "renderSignText" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE) })
    private void labyMod$setDefaultTextEdgeStrength(final gw $$0, final dee $$1, final elp $$2, final foe $$3, final int $$4, final int $$5, final int $$6, final boolean $$7, final CallbackInfo ci) {
        MinecraftUtil.updateTextEdgeStrength(0.5f);
    }
}
