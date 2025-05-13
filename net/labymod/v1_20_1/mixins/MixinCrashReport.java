// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.crash.DefaultCrashReportAppenderIterable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.crash.CrashReportAppenderIterable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ o.class })
public class MixinCrashReport
{
    private CrashReportAppenderIterable labyMod$crashReportAppenderIterable;
    
    @Inject(method = { "getDetails(Ljava/lang/StringBuilder;)V" }, at = { @At("TAIL") })
    private void labyMod$appendCrashReport(final StringBuilder builder, final CallbackInfo ci) {
        final CrashReportAppenderIterable crashReportAppenderRegistry = this.labyMod$getCrashReportAppenderRegistry();
        if (crashReportAppenderRegistry instanceof final DefaultCrashReportAppenderIterable appenderRegistry) {
            appenderRegistry.append(builder);
        }
    }
    
    private CrashReportAppenderIterable labyMod$getCrashReportAppenderRegistry() {
        if (this.labyMod$crashReportAppenderIterable == null) {
            this.labyMod$crashReportAppenderIterable = Laby.references().crashReportAppenderIterable();
        }
        return this.labyMod$crashReportAppenderIterable;
    }
}
