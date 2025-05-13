// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.io.IOException;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glb.class })
public abstract class MixinGameRendererPreloadUIShaders
{
    @Shadow
    @Final
    private fmg i;
    
    @Inject(method = { "preloadUiShader" }, at = { @At("TAIL") })
    private void labyMod$preloadUiShader(final avy provider, final CallbackInfo ci) {
        try {
            this.i.ab().a(provider, new gmd[] { gkv.g });
        }
        catch (final gmc.b | IOException exception) {
            throw new RuntimeException("Could not preload shaders for loading UI (labymod)", exception);
        }
    }
}
