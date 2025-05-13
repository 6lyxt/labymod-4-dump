// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ConnectableServerData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;

@Mixin({ ecv.class })
public abstract class MixinConnectScreen implements AddressResolveCallback
{
    @Shadow
    volatile boolean p;
    
    @Shadow
    protected abstract void shadow$a(final qk p0);
    
    @Override
    public void updateStatus(final Component status) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        this.shadow$a((qk)mapper.toMinecraftComponent(status));
    }
    
    @Override
    public void abort() {
        this.p = true;
    }
    
    @Inject(method = { "startConnecting" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$fireServerLoginEvent(final edw previousScreen, final dyr minecraft, final end address, final emx data, final CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        final Component disconnectReason = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData))).getDisconnectReason();
        if (disconnectReason != null) {
            dyr.D().a((edw)new edc(previousScreen, qj.i, (qk)disconnectReason));
            ci.cancel();
        }
    }
}
