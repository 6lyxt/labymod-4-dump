// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ grd.class })
public class MixinGameRendererScreenContext
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;getScaledXPos(Lcom/mojang/blaze3d/platform/Window;)D", shift = At.Shift.BEFORE) })
    private void labyMod$disableScreenContext(final fqg $$0, final boolean $$1, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;clearDepthTexture(Lcom/mojang/blaze3d/textures/GpuTexture;D)V", shift = At.Shift.BEFORE, ordinal = 0) })
    private void labyMod$enableScreenContext(final fqg $$0, final boolean $$1, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
