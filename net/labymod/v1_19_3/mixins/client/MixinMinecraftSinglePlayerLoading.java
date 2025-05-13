// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ejf.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final String $$0, final dve.c $$1, final ajp $$2, final acz $$3, final boolean $$4, final CallbackInfo ci) {
        final eyz level = ejf.N().s;
        if (level != null) {
            level.U();
        }
    }
}
