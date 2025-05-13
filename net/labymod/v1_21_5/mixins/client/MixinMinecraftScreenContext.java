// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqq.class })
public class MixinMinecraftScreenContext
{
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFog(Lnet/minecraft/client/renderer/FogParameters;)V", shift = At.Shift.AFTER) })
    private void labyMod$enableScreenContext(final boolean running, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;isMinimized()Z", shift = At.Shift.BEFORE) })
    private void labyMod$disableScreenContext(final boolean running, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
}
