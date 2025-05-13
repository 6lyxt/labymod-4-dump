// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ awz.class })
public class MixinGuiConnecting
{
    @Shadow
    private boolean h;
    
    @Inject(method = { "connect" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$fireServerLoginEvent(final String host, final int port, final CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(ave.A().D());
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(host, port)).build();
        }
        final Component disconnectReason = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData))).getDisconnectReason();
        if (disconnectReason != null) {
            ave.A().a((axu)new axh(ave.A().m, "connect.failed", (eu)disconnectReason));
            this.h = true;
            ci.cancel();
        }
    }
}
