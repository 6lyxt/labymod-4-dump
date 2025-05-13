// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.game.supplement.ServerSwitchPromptResponsePacket;
import net.labymod.serverapi.core.model.supplement.ServerSwitchPrompt;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi.ServerSwitchActivity;
import net.labymod.api.client.network.server.ConnectableServerData;
import java.util.Map;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.ServerSwitchPromptPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class ServerSwitchPromptPacketHandler implements PacketHandler<ServerSwitchPromptPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final ServerSwitchPromptPacket packet) {
        final LabyAPI labyAPI = Laby.labyAPI();
        final ServerData currentServerData = labyAPI.serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        final OtherConfig otherConfig = LabyConfigProvider.INSTANCE.get().other();
        final Map<String, Boolean> serverSwitchChoices = otherConfig.serverSwitchChoices().get();
        final ServerSwitchPrompt prompt = packet.prompt();
        final String rememberedCombination = currentServerData.actualAddress().toString() + "#" + prompt.getAddress();
        final Boolean savedChoice = serverSwitchChoices.get(rememberedCombination);
        if (savedChoice != null) {
            if (savedChoice) {
                labyAPI.serverController().joinServer(ConnectableServerData.builder().address(prompt.getAddress()).build());
            }
            this.sendResponse(packet, savedChoice);
            return;
        }
        final ServerSwitchActivity activity = new ServerSwitchActivity(packet.prompt(), currentServerData, value -> this.sendResponse(packet, value));
        labyAPI.minecraft().minecraftWindow().displayScreen(activity);
    }
    
    private void sendResponse(final ServerSwitchPromptPacket packet, final boolean value) {
        Laby.references().labyModProtocolService().sendLabyModPacket((Packet)new ServerSwitchPromptResponsePacket(packet, value));
    }
}
