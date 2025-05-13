// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqq.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doWorldLoad" }, at = { @At("HEAD") })
    private void labyMod$loadSinglePlayerWorld(final fah.c $$1, final auz $$2, final amq $$3, final boolean $$4, final CallbackInfo ci) {
        final glo level = fqq.Q().s;
        if (level != null) {
            level.ad();
        }
    }
}
