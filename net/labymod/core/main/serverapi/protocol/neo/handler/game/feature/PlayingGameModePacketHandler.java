// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLobbyEvent;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.feature.PlayingGameModePacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class PlayingGameModePacketHandler implements PacketHandler<PlayingGameModePacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final PlayingGameModePacket playingGameModePacket) {
        final ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null) {
            return;
        }
        final LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session == null) {
            return;
        }
        session.sendCurrentServer(serverData, playingGameModePacket.getGameMode(), false);
        Laby.fireEvent(new ServerLobbyEvent(ConnectableServerData.from(serverData), playingGameModePacket.getGameMode()));
    }
}
