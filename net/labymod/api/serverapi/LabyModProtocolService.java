// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.serverapi;

import net.labymod.api.client.component.Component;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.serverapi.api.AbstractProtocolService;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;

@Referenceable
public abstract class LabyModProtocolService extends AbstractLabyModProtocolService
{
    static UUID DUMMY_PLAYER;
    
    protected LabyModProtocolService() {
        super(AbstractProtocolService.Side.CLIENT);
    }
    
    public void send(@NotNull final PayloadChannelIdentifier payloadChannelIdentifier, @NotNull final PayloadWriter payloadWriter) {
        this.send(payloadChannelIdentifier, LabyModProtocolService.DUMMY_PLAYER, payloadWriter);
    }
    
    public void send(final Protocol protocol, final Packet packet) {
        protocol.sendPacket(LabyModProtocolService.DUMMY_PLAYER, packet);
    }
    
    public void sendLabyModPacket(final Packet packet) {
        this.send((Protocol)this.labyModProtocol, packet);
    }
    
    @NotNull
    public abstract PayloadTranslationRegistry translationRegistry();
    
    public abstract Component mapComponent(final ServerAPIComponent p0);
    
    public abstract boolean readPayload(final PayloadChannelIdentifier p0, final byte[] p1);
    
    static {
        LabyModProtocolService.DUMMY_PLAYER = new UUID(0L, 0L);
    }
}
