// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.multiplayer;

import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.resources.ResourceLocation;
import io.netty.buffer.ByteBuf;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dwu.class })
public abstract class MixinClientPacketListener
{
    @Shadow
    private dwt g;
    private ClientNetworkPacketListener labyMod$networkPacketListener;
    
    @Inject(method = { "handleSetPlayerTeamPacket" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$preventNPEViaVersion(final ri packet, final CallbackInfo ci) {
        if (this.g == null) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V" }, at = @At("TAIL"))
    private void login(final px packet, final InsertInfo ci) {
        this.labyMod$networkPacketListener().onLoginOrServerSwitch();
    }
    
    @Insert(method = { "handleCustomPayload(Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V", shift = At.Shift.AFTER))
    private void payloadReceive(final pk packet, final InsertInfo ci) {
        this.labyMod$networkPacketListener().onPayloadReceive(this.labyMod$convertResourceLocation(packet.b()), packet.c().copy());
    }
    
    @Insert(method = { "handleCustomPayload(Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;)V" }, at = @At(remap = false, value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void handleCustomPayloadChannel(final pk packet, final InsertInfo insertInfo) {
        this.labyMod$networkPacketListener().onCustomPayloadReceive(this.labyMod$convertResourceLocation(packet.b()), (ByteBuf)packet.c(), insertInfo);
    }
    
    private ResourceLocation labyMod$convertResourceLocation(final vk location) {
        return (ResourceLocation)location;
    }
    
    @Redirect(method = { "handleRotateMob" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V"))
    private void labyMod$oldHeadRotation(final aqa entity, final float yaw, final int pitch) {
        this.labyMod$networkPacketListener().onEntityRotate((Entity)entity, yaw, pitch);
    }
    
    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
}
