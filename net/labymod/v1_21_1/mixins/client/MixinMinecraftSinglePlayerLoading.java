// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fgo.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final erf.c $$1, final atp $$2, final alp $$3, final boolean $$4, final CallbackInfo ci) {
        final fzf level = fgo.Q().r;
        if (level != null) {
            level.Y();
        }
    }
}
