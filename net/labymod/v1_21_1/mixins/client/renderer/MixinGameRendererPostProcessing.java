// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ges.class })
public class MixinGameRendererPostProcessing
{
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", shift = At.Shift.AFTER) })
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(final fgf tracker, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(tracker.a(false));
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER) })
    private void labyMod$fireWorldPostProcessingScreenEvent(final fgf tracker, final boolean $$1, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(tracker.a(false));
    }
}
