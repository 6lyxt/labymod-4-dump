// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ enb.class })
public class MixinGameRendererPostProcessing
{
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V", shift = At.Shift.AFTER) })
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(final float partialTicks, final long $$1, final dql $$2, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(partialTicks);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER) })
    private void labyMod$fireWorldPostProcessingScreenEvent(final float partialTicks, final long $$1, final boolean $$2, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(partialTicks);
    }
}
