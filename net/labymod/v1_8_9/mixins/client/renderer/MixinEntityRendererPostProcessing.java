// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfk.class })
public class MixinEntityRendererPostProcessing
{
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE) })
    private void labyMod$fireBeforeHandProcessingScreenEvent(final int lvt_1_1_, final float partialTicks, final long lvt_3_1_, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(partialTicks);
    }
    
    @Inject(method = { "updateCameraAndRender" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntityOutlineFramebuffer()V", shift = At.Shift.AFTER) })
    private void labyMod$fireWorldPostProcessingScreenEvent(final float partialTicks, final long lvt_2_1_, final CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(partialTicks);
    }
}
