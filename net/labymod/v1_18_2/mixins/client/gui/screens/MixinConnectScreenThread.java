// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.net.InetSocketAddress;
import net.labymod.core.client.network.server.connect.ConnectAddressResolver;
import net.labymod.core.client.network.server.connect.AddressResolveCallback;
import java.util.Optional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net/minecraft/client/gui/screens/ConnectScreen$1" })
public class MixinConnectScreenThread
{
    @Shadow
    @Final
    ecv c;
    
    @Redirect(method = { "run" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/resolver/ServerNameResolver;resolveAddress(Lnet/minecraft/client/multiplayer/resolver/ServerAddress;)Ljava/util/Optional;"))
    private Optional<enc> labyMod$resolveAddress(final enf resolver, final end address) {
        final InetSocketAddress resolved = ConnectAddressResolver.resolve(address.a(), address.b(), (AddressResolveCallback)this.c);
        return (resolved == null) ? Optional.empty() : Optional.of(enc.a(resolved));
    }
    
    @ModifyArg(method = { "lambda$run$0(Lnet/minecraft/client/Minecraft;)V", "lambda$run$1(Lnet/minecraft/client/Minecraft;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public qk labyMod$fireServerKickEvent(final qk reason) {
        final ServerData serverData = Laby.labyAPI().serverController().createServerData(dyr.D().F());
        if (serverData == null) {
            return reason;
        }
        return (qk)Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component)reason, ServerKickEvent.Context.PRE_LOGIN)).reason();
    }
}
