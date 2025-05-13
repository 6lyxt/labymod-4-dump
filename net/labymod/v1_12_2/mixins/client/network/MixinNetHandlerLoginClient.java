// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.network;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bry.class })
public class MixinNetHandlerLoginClient
{
    @ModifyArg(method = { "onDisconnect" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiDisconnected;<init>(Lnet/minecraft/client/gui/GuiScreen;Ljava/lang/String;Lnet/minecraft/util/text/ITextComponent;)V"), index = 2)
    public hh labyMod$fireServerKickEvent(final hh reason) {
        final ServerData serverData = Laby.labyAPI().serverController().createServerData(bib.z().C());
        if (serverData == null) {
            return reason;
        }
        return (hh)Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(serverData), (Component)reason, ServerKickEvent.Context.LOGIN)).reason();
    }
}
