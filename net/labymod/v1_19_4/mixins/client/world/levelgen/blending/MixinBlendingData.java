// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.world.levelgen.blending;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dic.class })
public class MixinBlendingData
{
    @Inject(method = { "getOrUpdateBlendingData" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$getOrUpdateBlendingData(final aiw region, final int x, final int z, final CallbackInfoReturnable<dic> cir) {
        final ddn chunk = region.a(x, z);
        final dds status = MinecraftUtil.getHighestGeneratedStatus(chunk);
        if (chunk.t() != null && status.b(dds.f)) {
            return;
        }
        cir.setReturnValue((Object)null);
    }
}
