// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.v1_21_3.client.multiplayer.server.payload.CustomPayloadHolder;
import net.labymod.v1_21_3.client.multiplayer.server.LabyModCustomPacketPayload;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net/minecraft/network/protocol/common/custom/CustomPacketPayload$1" })
public abstract class MixinCustomPacketPayload<B extends ws> implements zt<B, abf>
{
    @Inject(method = { "writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends abf> void labyMod$encodeCustomPayload(final B buffer, final abf.b<T> type, final T payload, final CallbackInfo ci) {
        if (payload instanceof final LabyModCustomPacketPayload labyModPayload) {
            final alz channelId = type.a();
            final zt<ws, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
            buffer.a(channelId);
            if (codec == null) {
                if (IdeUtil.RUNNING_IN_IDE) {
                    IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
                }
                final zt<ws, abg> discardedCodec = (zt<ws, abg>)abg.a(channelId, 1048576);
                discardedCodec.encode((Object)buffer, (Object)new abg(channelId));
            }
            else {
                codec.encode((Object)buffer, (Object)labyModPayload);
            }
            ci.cancel();
        }
    }
    
    @Inject(method = { "decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$decodeCustomPayload(final B buffer, final CallbackInfoReturnable<abf> cir) {
        final ws copiedBuffer = new ws(buffer.copy());
        final alz payloadChannelId = copiedBuffer.q();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive((ResourceLocation)payloadChannelId, copiedBuffer.copy());
        final zt<ws, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(payloadChannelId);
        if (codec != null) {
            cir.setReturnValue((Object)codec.decode((Object)copiedBuffer));
            buffer.y();
        }
    }
}
