// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.intave.handler;

import net.labymod.serverapi.api.packet.Packet;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class IntaveClientConfigReceivedPacketHandler implements PacketHandler<IntaveClientConfigReceivedPacket>
{
    private final IntaveProtocol intaveProtocol;
    
    public IntaveClientConfigReceivedPacketHandler(final IntaveProtocol intaveProtocol) {
        this.intaveProtocol = intaveProtocol;
    }
    
    public void handle(@NotNull final UUID uuid, @NotNull final IntaveClientConfigReceivedPacket packet) {
        this.intaveProtocol.setReceivedConfigPacket(true);
        this.intaveProtocol.setHasPermission(packet.isReceived());
    }
}
