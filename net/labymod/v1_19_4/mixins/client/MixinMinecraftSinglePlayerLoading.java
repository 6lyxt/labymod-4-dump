// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ emh.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final String $$0, final dye.c $$1, final aks $$2, final adx $$3, final boolean $$4, final CallbackInfo ci) {
        final fdj level = emh.N().s;
        if (level != null) {
            level.T();
        }
    }
}
