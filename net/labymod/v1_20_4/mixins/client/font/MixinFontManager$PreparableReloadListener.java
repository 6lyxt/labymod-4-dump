// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.font;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ezu.class })
public class MixinFontManager$PreparableReloadListener
{
    @Inject(method = { "apply" }, at = { @At("TAIL") })
    private void labyMod$onFontApply(final ezu.d $$0, final bgs $$1, final CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
