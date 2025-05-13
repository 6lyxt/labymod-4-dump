// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fnx.class })
public class MixinGameRendererBufferSource
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;F)V", shift = At.Shift.AFTER) })
    private void labyMod$endBatch(final float param0, final long param1, final boolean param2, final CallbackInfo ci) {
        Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DBufferSource().endBuffer();
    }
}
