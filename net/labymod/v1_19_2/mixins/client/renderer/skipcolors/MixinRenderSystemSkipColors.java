// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer.skipcolors;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderSystem.class })
public class MixinRenderSystemSkipColors
{
    @Inject(method = { "setShaderColor" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$skipColors(final float $$0, final float $$1, final float $$2, final float $$3, final CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            ci.cancel();
        }
    }
}
