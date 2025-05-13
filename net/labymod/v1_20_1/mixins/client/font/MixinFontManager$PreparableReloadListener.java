// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.font;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ erm.class })
public class MixinFontManager$PreparableReloadListener
{
    @Inject(method = { "apply" }, at = { @At("TAIL") })
    private void labyMod$onFontApply(final erm.d $$0, final ban $$1, final CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
