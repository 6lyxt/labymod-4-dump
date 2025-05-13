// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.datafixers.util.Function4;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ djz.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doLoadLevel" }, at = { @At("HEAD") })
    public void labyMod$loadSinglePlayerWorld(final String param0, final gn.b param1, final Function<cyg.a, brk> param2, final Function4<cyg.a, gn.b, ach, brk, cyn> param3, final boolean param4, final djz.a param5, final CallbackInfo ci) {
        final dwt level = djz.C().r;
        if (level != null) {
            level.S();
        }
    }
}
