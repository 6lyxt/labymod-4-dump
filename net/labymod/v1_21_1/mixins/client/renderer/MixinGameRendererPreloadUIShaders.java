// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ges.class })
public abstract class MixinGameRendererPreloadUIShaders
{
    @Shadow
    @Nullable
    private static gfn V;
    
    @Shadow
    protected abstract gfn a(final auh p0, final String p1, final fbn p2);
    
    @Inject(method = { "preloadUiShader" }, at = { @At("TAIL") })
    private void labyMod$preloadUiShader(final auh provider, final CallbackInfo ci) {
        MixinGameRendererPreloadUIShaders.V = this.a(provider, "position_color_tex_lightmap", fbg.k);
    }
}
