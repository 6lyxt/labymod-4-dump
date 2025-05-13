// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.v1_21_5.client.multiplayer.server.payload.CustomPayloadHolder;
import net.labymod.v1_21_5.client.multiplayer.server.LabyModCustomPacketPayload;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net/minecraft/network/protocol/common/custom/CustomPacketPayload$1" })
public abstract class MixinCustomPacketPayload<B extends vy> implements ze<B, aat>
{
    @Inject(method = { "writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends aat> void labyMod$encodeCustomPayload(final B buffer, final aat.b<T> type, final T payload, final CallbackInfo ci) {
        if (payload instanceof final LabyModCustomPacketPayload labyModPayload) {
            final alr channelId = type.a();
            final ze<vy, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
            buffer.a(channelId);
            if (codec == null) {
                if (IdeUtil.RUNNING_IN_IDE) {
                    IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
                }
                final ze<vy, aau> discardedCodec = (ze<vy, aau>)aau.a(channelId, 1048576);
                discardedCodec.encode((Object)buffer, (Object)new aau(channelId));
            }
            else {
                codec.encode((Object)buffer, (Object)labyModPayload);
            }
            ci.cancel();
        }
    }
    
    @Inject(method = { "decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$decodeCustomPayload(final B buffer, final CallbackInfoReturnable<aat> cir) {
        final vy copiedBuffer = new vy(buffer.copy());
        final alr payloadChannelId = copiedBuffer.q();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive((ResourceLocation)payloadChannelId, copiedBuffer.copy());
        final ze<vy, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(payloadChannelId);
        if (codec != null) {
            cir.setReturnValue((Object)codec.decode((Object)copiedBuffer));
            buffer.y();
        }
    }
}
