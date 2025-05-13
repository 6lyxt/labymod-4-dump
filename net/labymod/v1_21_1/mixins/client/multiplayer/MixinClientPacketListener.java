// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.multiplayer;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fzg.class })
public abstract class MixinClientPacketListener
{
    @Shadow
    private fzf t;
    private ClientNetworkPacketListener labyMod$networkPacketListener;
    
    @Inject(method = { "handleSetPlayerTeamPacket" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$preventNPEViaVersion(final afh packet, final CallbackInfo ci) {
        if (this.t == null) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V" }, at = @At("TAIL"))
    private void labyMod$login(final adl packet, final InsertInfo ci) {
        this.labyMod$networkPacketListener().onLoginOrServerSwitch();
    }
    
    @Redirect(method = { "handleLogin" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"))
    public void labyMod$hideUnsecureServerToast(final fkw toastComponent, final fkv toast) {
        if (Laby.labyAPI().config().notifications().hideChatTrustToast().get()) {
            return;
        }
        toastComponent.a(toast);
    }
    
    @Redirect(method = { "handleRotateMob" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V"))
    private void labyMod$oldHeadRotation(final bsr entity, final float yaw, final int pitch) {
        this.labyMod$networkPacketListener().onEntityRotate((Entity)entity, yaw, pitch);
    }
    
    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
}
