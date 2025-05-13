// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.datafixers.util.Function4;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dvp.class })
public class MixinMinecraftSinglePlayerLoading
{
    @Inject(method = { "doLoadLevel" }, at = { @At("HEAD") })
    public void labyMod$loadSinglePlayerWorld(final String param0, final gx.b param1, final Function<dib.a, bwd> param2, final Function4<dib.a, gx.b, adt, bwd, dii> param3, final boolean param4, final dvp.b param5, final CallbackInfo ci) {
        final eji level = dvp.C().r;
        if (level != null) {
            level.U();
        }
    }
}
