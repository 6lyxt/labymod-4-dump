// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.network.play.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ lt.class })
public class MixinCPacketResourcePackStatus
{
    @Inject(method = { "<init>(Lnet/minecraft/network/play/client/CPacketResourcePackStatus$Action;)V" }, at = { @At("TAIL") })
    private void labyMod$disableCustomFont(final lt.a action, final CallbackInfo ci) {
        if (action == lt.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
