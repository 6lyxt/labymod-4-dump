// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public class MixinMinecraftFastWorldLoading
{
    @Redirect(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V"))
    private void labyMod$respawnCode() {
        if (Laby.labyAPI().config().ingame().fastWorldLoading().get()) {
            return;
        }
        System.gc();
    }
}
