// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net/minecraft/client/gui/screens/ConnectScreen$1" })
public class MixinConnectScreenThread
{
    @ModifyArg(method = { "lambda$run$1()V", "lambda$run$2(Ljava/lang/String;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public nr labyMod$fireServerKickEvent(final nr reason) {
        final ServerData serverData = Laby.labyAPI().serverController().createServerData(djz.C().E());
        if (serverData == null) {
            return reason;
        }
        return (nr)Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component)reason, ServerKickEvent.Context.PRE_LOGIN)).reason();
    }
}
