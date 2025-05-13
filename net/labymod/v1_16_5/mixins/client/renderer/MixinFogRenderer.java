// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dzy.class })
public class MixinFogRenderer
{
    @Inject(method = { "setupNoFog" }, at = { @At("TAIL") })
    private static void labyMod$setupNoFog(final CallbackInfo ci) {
        RenderSystem.fogStart(Float.MAX_VALUE);
    }
}
