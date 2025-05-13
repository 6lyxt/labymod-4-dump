// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ evi.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final egm.c $$1, final apt $$2, final aic $$3, final boolean $$4, final CallbackInfo ci) {
        final fns level = evi.O().r;
        if (level != null) {
            level.W();
        }
    }
}
