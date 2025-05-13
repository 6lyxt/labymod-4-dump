// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import net.labymod.api.service.Registry;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import java.util.Iterator;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class InteractionMenuPacketHandler implements PacketHandler<InteractionMenuPacket>
{
    private final InteractionMenuRegistry interactionMenuRegistry;
    
    public InteractionMenuPacketHandler() {
        this.interactionMenuRegistry = Laby.references().interactionMenuRegistry();
        Laby.references().eventBus().registerListener(this);
    }
    
    public void handle(@NotNull final UUID uuid, @NotNull final InteractionMenuPacket packet) {
        this.interactionMenuRegistry.unregisterServerBulletPoints();
        for (final InteractionMenuEntry entry : packet.getEntries()) {
            ((Registry<ServerBulletPoint>)this.interactionMenuRegistry).register(new ServerBulletPoint(entry));
        }
    }
    
    @Subscribe
    public void onNetworkDisconnect(final NetworkDisconnectEvent event) {
        this.interactionMenuRegistry.unregisterServerBulletPoints();
    }
}
