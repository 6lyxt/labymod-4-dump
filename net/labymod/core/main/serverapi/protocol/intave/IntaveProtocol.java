// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.intave;

import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import java.util.UUID;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.serverapi.TranslationListener;
import net.labymod.core.main.serverapi.protocol.intave.translation.IntaveConfigTranslationListener;
import net.labymod.api.serverapi.TranslationProtocol;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.core.main.serverapi.protocol.intave.handler.IntaveClientConfigReceivedPacketHandler;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.serverapi.api.packet.Direction;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigPacket;
import net.labymod.serverapi.api.AbstractProtocolService;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.serverapi.api.Protocol;

@Singleton
@Referenceable
public class IntaveProtocol extends Protocol
{
    private final LabyModProtocolService protocolService;
    private boolean hasPermission;
    private boolean receivedConfigPacket;
    
    public IntaveProtocol(@NotNull final LabyModProtocolService protocolService) {
        super((AbstractProtocolService)protocolService, PayloadChannelIdentifier.create("minecraft", "intave"));
        this.protocolService = protocolService;
        this.initialize();
        protocolService.registry().registerProtocol((Protocol)this);
    }
    
    private void initialize() {
        this.registerPacket(0, (Class)IntaveClientConfigPacket.class, Direction.SERVERBOUND);
        this.registerPacket(1, (Class)IntaveClientConfigReceivedPacket.class, Direction.CLIENTBOUND, (PacketHandler)new IntaveClientConfigReceivedPacketHandler(this));
        final TranslationProtocol translationProtocol = new TranslationProtocol(this.identifier, this);
        this.protocolService.translationRegistry().register(translationProtocol);
        translationProtocol.registerListener(new IntaveConfigTranslationListener());
    }
    
    public void sendPacket(final Packet packet) {
        this.sendPacket(LabyModProtocolService.DUMMY_UUID, packet);
    }
    
    @Nullable
    public Packet handleIncomingPayload(@NotNull final UUID sender, @NotNull final PayloadReader reader) {
        return null;
    }
    
    public void sendPacket(@NotNull final UUID recipient, @NotNull final Packet packet) {
        this.protocolService.afterPacketSent((Protocol)this, packet, recipient);
    }
    
    public boolean hasPermission(final boolean enabled) {
        if (this.receivedConfigPacket) {
            return this.hasPermission && enabled;
        }
        return enabled;
    }
    
    public boolean hasPermission(final BooleanSupplier function) {
        if (this.receivedConfigPacket) {
            return this.hasPermission && function.getAsBoolean();
        }
        return function.getAsBoolean();
    }
    
    public void setHasPermission(final boolean hasPermission) {
        this.hasPermission = hasPermission;
    }
    
    public void setReceivedConfigPacket(final boolean receivedConfigPacket) {
        this.receivedConfigPacket = receivedConfigPacket;
    }
}
