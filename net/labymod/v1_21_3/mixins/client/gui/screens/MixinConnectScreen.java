// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
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

@Mixin({ fsx.class })
public abstract class MixinConnectScreen implements AddressResolveCallback
{
    @Shadow
    volatile boolean w;
    
    @Shadow
    protected abstract void shadow$a(final xv p0);
    
    @Override
    public void updateStatus(final Component status) {
        final ComponentMapper mapper = Laby.references().componentMapper();
        this.shadow$a((xv)mapper.toMinecraftComponent(status));
    }
    
    @Override
    public void abort() {
        this.w = true;
    }
    
    @Inject(method = { "startConnecting" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$fireServerLoginEvent(final fty previousScreen, final fmg minecraft, final ghc address, final gfz data, final boolean quickPlay, @Nullable final ggd transferState, final CallbackInfo ci) {
        ServerData serverData = Laby.labyAPI().serverController().createServerData(data);
        if (serverData == null) {
            serverData = ConnectableServerData.builder().address(new ServerAddress(address.a(), address.b())).build();
        }
        final CookieStorage cookieStorage = MinecraftUtil.fromMinecraft(transferState);
        final ServerLoginEvent event = Laby.fireEvent(new ServerLoginEvent(ConnectableServerData.from(serverData), cookieStorage));
        final Component disconnectReason = event.getDisconnectReason();
        if (disconnectReason != null) {
            fmg.Q().a((fty)new ftf(previousScreen, xu.r, (xv)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(disconnectReason)));
            ci.cancel();
        }
    }
}
