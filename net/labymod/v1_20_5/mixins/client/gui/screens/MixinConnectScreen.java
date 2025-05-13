// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ConnectableServerData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;

@Mixin({ flx.class })
public abstract class MixinConnectScreen implements AddressResolveCallback
{
    @Shadow
    volatile boolean v;
    
    @Shadow
    protected abstract void shadow$a(final xp p0);
    
    @Override
    public void updateStatus(final Component status) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        this.shadow$a((xp)mapper.toMinecraftComponent(status));
    }
    
    @Override
    public void abort() {
        this.v = true;
    }
    
    @Inject(method = { "startConnecting" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$fireServerLoginEvent(final fne previousScreen, final ffg minecraft, final fzn address, final fyk data, final boolean quickPlay, @Nullable final fyo transferState, final CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        final CookieStorage cookieStorage = MinecraftUtil.fromMinecraft(transferState);
        final ServerLoginEvent event = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData), cookieStorage));
        final Component disconnectReason = event.getDisconnectReason();
        if (disconnectReason != null) {
            ffg.Q().a((fne)new fmf(previousScreen, xo.r, (xp)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }
}
