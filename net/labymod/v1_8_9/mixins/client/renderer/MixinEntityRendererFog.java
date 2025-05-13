// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfk.class })
public class MixinEntityRendererFog
{
    @Shadow
    private float Q;
    @Shadow
    private float R;
    @Shadow
    private float S;
    
    @Inject(method = { "updateFogColor" }, at = { @At("TAIL") })
    private void labyMod$storeFogColor(final float lvt_1_1_, final CallbackInfo ci) {
        Laby.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().fog().updateColor(this.Q, this.R, this.S, 1.0f);
    }
}
