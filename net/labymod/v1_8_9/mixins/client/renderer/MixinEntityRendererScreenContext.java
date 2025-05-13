// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfk.class })
public class MixinEntityRendererScreenContext
{
    @Inject(method = { "updateCameraAndRender" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void labyMod$disableScreenContext(final float p_updateCameraAndRender_1_, final long p_updateCameraAndRender_2_, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
    
    @Inject(method = { "updateCameraAndRender" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", shift = At.Shift.AFTER) })
    private void labyMod$enableScreenContext(final float p_updateCameraAndRender_1_, final long p_updateCameraAndRender_2_, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
