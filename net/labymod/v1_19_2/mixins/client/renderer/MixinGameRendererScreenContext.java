// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ezl.class })
public class MixinGameRendererScreenContext
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledHeight()I", shift = At.Shift.BEFORE, ordinal = 0) })
    private void labyMod$disableScreenContext(final float param0, final long param1, final boolean param2, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(false);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V", shift = At.Shift.BEFORE, ordinal = 0) })
    private void labyMod$enableScreenContext(final float param0, final long param1, final boolean param2, final CallbackInfo ci) {
        ScreenUtil.setScreenContext(true);
    }
}
