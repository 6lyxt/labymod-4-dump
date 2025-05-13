// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ b.class })
public class MixinCrashReport
{
    private CrashReportAppenderIterable labyMod$crashReportAppenderIterable;
    
    @Inject(method = { "getSectionsInStringBuilder" }, at = { @At("TAIL") })
    private void labyMod$appendCrashReport(final StringBuilder builder, final CallbackInfo ci) {
        final CrashReportAppenderIterable crashReportAppenderIterable = this.labyMod$getCrashReportAppenderRegistry();
        if (!(crashReportAppenderIterable instanceof DefaultCrashReportAppenderIterable)) {
            return;
        }
        ((DefaultCrashReportAppenderIterable)crashReportAppenderIterable).append(builder);
    }
    
    private CrashReportAppenderIterable labyMod$getCrashReportAppenderRegistry() {
        if (this.labyMod$crashReportAppenderIterable == null) {
            this.labyMod$crashReportAppenderIterable = Laby.references().crashReportAppenderIterable();
        }
        return this.labyMod$crashReportAppenderIterable;
    }
}
