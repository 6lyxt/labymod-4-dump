// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity.boss;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfc.class })
public class MixinBossStatus
{
    @Inject(method = { "setBossStatus" }, at = { @At("HEAD") })
    private static void labyMod$registerBossBar(final uc displayData, final boolean hasColorModifier, final CallbackInfo ci) {
        ((VersionedBossBarRegistry)Laby.references().bossBarRegistry()).registerBossBar(displayData);
    }
}
