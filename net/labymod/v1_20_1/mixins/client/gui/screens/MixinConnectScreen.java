// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui.screens;

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

@Mixin({ etl.class })
public abstract class MixinConnectScreen implements AddressResolveCallback
{
    @Shadow
    volatile boolean o;
    
    @Shadow
    protected abstract void shadow$a(final sw p0);
    
    @Override
    public void updateStatus(final Component status) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        this.shadow$a((sw)mapper.toMinecraftComponent(status));
    }
    
    @Override
    public void abort() {
        this.o = true;
    }
    
    @Inject(method = { "startConnecting" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$fireServerLoginEvent(final euq previousScreen, final enn minecraft, final fga address, final ffd data, final boolean quickPlay, final CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        final Component disconnectReason = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData))).getDisconnectReason();
        if (disconnectReason != null) {
            enn.N().a((euq)new ett(previousScreen, sv.p, (sw)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }
}
