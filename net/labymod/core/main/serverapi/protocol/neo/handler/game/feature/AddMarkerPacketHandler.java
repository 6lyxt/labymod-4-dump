// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labyconnect.object.marker.MarkerObject;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class AddMarkerPacketHandler implements PacketHandler<AddMarkerPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final AddMarkerPacket packet) {
        MarkerObject marker;
        if (packet.getTarget() != null) {
            final Entity entity = Laby.labyAPI().minecraft().clientWorld().getEntity(packet.getTarget()).orElse(null);
            if (entity == null) {
                return;
            }
            marker = MarkerObject.forEntity(packet.getSender(), entity, packet.isLarge());
        }
        else {
            marker = MarkerObject.fixed(packet.getSender(), new DoubleVector3(packet.getX() + 0.5f, packet.getY() + 1.0f, packet.getZ() + 0.5f), packet.isLarge());
        }
        LabyMod.references().markerService().displayMarker(marker);
    }
}
