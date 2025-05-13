// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ enb.class })
public abstract class MixinGameRendererPreloadUIShaders
{
    @Shadow
    @Nullable
    private static ent X;
    
    @Shadow
    protected abstract ent a(final adv p0, final String p1, final dqq p2);
    
    @Inject(method = { "preloadUiShader" }, at = { @At("TAIL") })
    private void labyMod$preloadUiShader(final adv provider, final CallbackInfo ci) {
        MixinGameRendererPreloadUIShaders.X = this.a(provider, "position_color_tex_lightmap", dqj.t);
    }
}
