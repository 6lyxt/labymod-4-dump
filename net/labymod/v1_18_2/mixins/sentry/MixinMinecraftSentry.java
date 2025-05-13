// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.sentry;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import io.sentry.Sentry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dyr.class })
public class MixinMinecraftSentry
{
    @Inject(method = { "crash" }, at = { @At("HEAD") })
    private static void labyMod$captureException(final q report, final CallbackInfo ci) {
        Sentry.captureException(report.b());
    }
}
