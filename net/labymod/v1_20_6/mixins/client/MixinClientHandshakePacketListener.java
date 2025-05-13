// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.v1_20_6.client.multiplayer.server.VersionedServerController;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fxw.class })
public class MixinClientHandshakePacketListener
{
    @ModifyArg(method = { "onDisconnect" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V"), index = 2)
    public xp labyMod$fireServerKickEvent(final xp reason) {
        final LabyAPI labyAPI = Laby.labyAPI();
        final ServerData data = ((VersionedServerController)labyAPI.serverController()).getConnectingServerData();
        if (data == null) {
            return reason;
        }
        final ComponentMapper componentMapper = labyAPI.minecraft().componentMapper();
        return (xp)componentMapper.toMinecraftComponent(Laby.fireEvent(new ServerKickEvent(ConnectableServerData.from(data), componentMapper.fromMinecraftComponent(reason), ServerKickEvent.Context.LOGIN)).reason());
    }
}
