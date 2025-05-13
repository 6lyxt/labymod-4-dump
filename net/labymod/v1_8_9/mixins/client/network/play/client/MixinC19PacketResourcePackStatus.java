// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.network.play.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ iu.class })
public class MixinC19PacketResourcePackStatus
{
    @Inject(method = { "<init>(Ljava/lang/String;Lnet/minecraft/network/play/client/C19PacketResourcePackStatus$Action;)V" }, at = { @At("TAIL") })
    private void labyMod$disableCustomFont(final String hash, final iu.a action, final CallbackInfo ci) {
        if (action == iu.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
