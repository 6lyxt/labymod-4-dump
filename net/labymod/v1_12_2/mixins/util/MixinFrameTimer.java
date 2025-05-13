// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.util;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.profiler.SampleLogger;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.MinecraftFrameTimer;

@Mixin({ rb.class })
public abstract class MixinFrameTimer implements MinecraftFrameTimer
{
    @Unique
    private final SampleLogger labyMod$logger;
    
    public MixinFrameTimer() {
        this.labyMod$logger = new SampleLogger(240);
    }
    
    @Inject(method = { "addFrame" }, at = { @At("HEAD") })
    private void labyMod$logSample(final long sample, final CallbackInfo ci) {
        this.labyMod$logger.log(sample);
    }
    
    @NotNull
    @Override
    public SampleLogger logger() {
        return this.labyMod$logger;
    }
}
