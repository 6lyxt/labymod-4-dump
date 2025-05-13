// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MixinMinecraftScreenContext
{
    @Inject(method = { "runGameLoop" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableTexture2D()V", shift = At.Shift.AFTER) })
    private void labyMod$enableScreenContext(final CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;unbindFramebuffer()V", shift = At.Shift.BEFORE) })
    private void labyMod$disableScreenContext(final CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
