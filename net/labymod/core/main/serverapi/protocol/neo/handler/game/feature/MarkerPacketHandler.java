// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.core.labyconnect.object.marker.MarkerService;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class MarkerPacketHandler implements PacketHandler<MarkerPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final MarkerPacket packet) {
        final MarkerService service = LabyMod.references().markerService();
        service.setSendType(packet.type());
    }
}
