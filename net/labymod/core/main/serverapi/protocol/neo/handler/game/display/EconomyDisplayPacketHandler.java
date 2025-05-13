// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.display;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.serverapi.EconomyUpdateEvent;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class EconomyDisplayPacketHandler implements PacketHandler<EconomyDisplayPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final EconomyDisplayPacket packet) {
        Laby.fireEvent(new EconomyUpdateEvent(packet.economy()));
    }
}
