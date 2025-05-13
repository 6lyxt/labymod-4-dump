// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.network;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.v1_21_5.client.multiplayer.server.VersionedServerController;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ vv.class })
public class MixinConnection
{
    @ModifyVariable(method = { "disconnect" }, at = @At("HEAD"), argsOnly = true)
    public xg labyMod$modifyReason(final xg component) {
        final ServerController serverController = Laby.labyAPI().serverController();
        final StorageServerData currentStorageServerData = serverController.getCurrentStorageServerData();
        ConnectableServerData serverData;
        if (currentStorageServerData != null) {
            serverData = currentStorageServerData;
        }
        else if (serverController.getCurrentServerData() != null) {
            serverData = ConnectableServerData.from(serverController.getCurrentServerData());
        }
        else {
            final ServerData data = ((VersionedServerController)serverController).getConnectingServerData();
            if (data == null) {
                return component;
            }
            serverData = ConnectableServerData.from(data);
        }
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        return (xg)componentMapper.toMinecraftComponent(Laby.fireEvent(new ServerKickEvent(serverData, componentMapper.fromMinecraftComponent(component), ServerKickEvent.Context.PLAY)).reason());
    }
}
