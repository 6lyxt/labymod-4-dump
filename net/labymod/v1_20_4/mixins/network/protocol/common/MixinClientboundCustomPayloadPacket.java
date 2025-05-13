// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.network.protocol.common;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_4.client.multiplayer.server.custompayload.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { xj.class }, priority = 1001)
public class MixinClientboundCustomPayloadPacket
{
    @Inject(method = { "readPayload" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;readUnknownPayload(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;") }, cancellable = true)
    private static void labyMod$fixCustomPayload(final ahg resourceLocation, final ui buffer, final CallbackInfoReturnable<ya> cir) {
        final byte[] data = new byte[buffer.readableBytes()];
        buffer.b(data);
        cir.setReturnValue((Object)LabyModCustomPacketPayload.of((ResourceLocation)resourceLocation, data));
    }
}
