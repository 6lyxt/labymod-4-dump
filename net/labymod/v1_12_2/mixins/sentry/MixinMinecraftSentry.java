// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.sentry;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import io.sentry.Sentry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MixinMinecraftSentry
{
    @Inject(method = { "displayCrashReport" }, at = { @At("HEAD") })
    private void labyMod$captureException(final b report, final CallbackInfo ci) {
        Sentry.captureException(report.b());
    }
}
