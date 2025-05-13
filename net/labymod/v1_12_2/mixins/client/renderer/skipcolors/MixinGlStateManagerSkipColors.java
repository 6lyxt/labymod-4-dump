// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.skipcolors;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bus.class })
public class MixinGlStateManagerSkipColors
{
    @Inject(method = { "color(FFFF)V" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$skipColors(final float lvt_0_1_, final float lvt_1_1_, final float lvt_2_1_, final float lvt_3_1_, final CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            ci.cancel();
        }
    }
}
