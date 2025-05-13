// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.multiplayer;

import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.entity.Entity;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glp.class })
public abstract class MixinClientPacketListener
{
    @Shadow
    private glo t;
    private ClientNetworkPacketListener labyMod$networkPacketListener;
    
    @Inject(method = { "handleSetPlayerTeamPacket" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$preventNPEViaVersion(final afy packet, final CallbackInfo ci) {
        if (this.t == null) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V" }, at = @At("TAIL"))
    private void labyMod$login(final adw packet, final InsertInfo ci) {
        this.labyMod$networkPacketListener().onLoginOrServerSwitch();
    }
    
    @WrapOperation(method = { "handleLogin" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/ToastManager;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V") })
    private void labyMod$hideUnsecureServerToast(final fwj toastManager, final fwi toast, final Operation<Void> original) {
        if (Laby.labyAPI().config().notifications().hideChatTrustToast().get()) {
            return;
        }
        original.call(new Object[] { toastManager, toast });
    }
    
    @Redirect(method = { "handleRotateMob" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V"))
    private void labyMod$oldHeadRotation(final bxe entity, final float yaw, final int pitch) {
        this.labyMod$networkPacketListener().onEntityRotate((Entity)entity, yaw, pitch);
    }
    
    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
}
