// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dyr.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doLoadLevel" }, at = { @At("HEAD") })
    public void labyMod$loadSinglePlayerWorld(final String $$0, final Function<dkp.a, zk.a> $$1, final Function<dkp.a, zk.c> $$2, final boolean $$3, final dyr.b $$4, final CallbackInfo ci) {
        final ems level = dyr.D().r;
        if (level != null) {
            level.T();
        }
    }
}
