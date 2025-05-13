// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { fiu.class }, priority = 999)
public class MixinClientCommonPacketListenerImpl
{
    @Shadow
    @Final
    protected eqv a;
    private ClientNetworkPacketListener labyMod$networkPacketListener;
    
    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
    
    private ResourceLocation labyMod$convertResourceLocation(final aew location) {
        return (ResourceLocation)location;
    }
    
    @Insert(method = { "handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$payloadReceive(final vh packet, final InsertInfo insertInfo) {
        final vw payload = packet.a();
        if (!(payload instanceof vx)) {
            vf.a((vd)packet, (su)this, (bfo)this.a);
            final so byteBuf = new so(Unpooled.buffer());
            payload.a(byteBuf);
            this.labyMod$networkPacketListener().onPayloadReceive(this.labyMod$convertResourceLocation(payload.a()), byteBuf.copy());
            this.labyMod$networkPacketListener().onCustomPayloadReceive(this.labyMod$convertResourceLocation(payload.a()), (ByteBuf)byteBuf, insertInfo);
        }
    }
    
    @Inject(method = { "send(Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V" }, at = { @At("TAIL") })
    private void labyMod$disableCustomFont(final vs.a action, final CallbackInfo ci) {
        if (action == vs.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
