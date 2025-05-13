// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ffg.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final epx.c $$1, final aua $$2, final amc $$3, final boolean $$4, final CallbackInfo ci) {
        final fxw level = ffg.Q().r;
        if (level != null) {
            level.Y();
        }
    }
}
