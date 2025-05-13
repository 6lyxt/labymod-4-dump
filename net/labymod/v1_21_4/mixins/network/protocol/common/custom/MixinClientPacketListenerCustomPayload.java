// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.network.protocol.common.custom;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.Laby;
import net.labymod.v1_21_4.client.multiplayer.server.LabyModCustomPacketPayload;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ggb.class })
public class MixinClientPacketListenerCustomPayload
{
    @Inject(method = { "handleUnknownCustomPayload" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$onHandleCustomPayload(final zz payload, final CallbackInfo ci) {
        if (payload instanceof final LabyModCustomPacketPayload labyModCustomPacketPayload) {
            final akv id = payload.a().a();
            final boolean handled = Laby.references().serverController().handleCustomPayload(PayloadChannelIdentifier.create(id.b(), id.a()), labyModCustomPacketPayload.getBuffer());
            if (handled) {
                ci.cancel();
            }
        }
    }
}
