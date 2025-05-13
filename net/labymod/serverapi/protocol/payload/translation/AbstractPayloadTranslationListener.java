// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.translation;

import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.serverapi.protocol.packet.Packet;
import net.labymod.serverapi.protocol.packet.protocol.Protocol;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class AbstractPayloadTranslationListener
{
    private final PayloadChannelIdentifier oldIdentifier;
    private final PayloadChannelIdentifier newIdentifier;
    
    @Deprecated
    public AbstractPayloadTranslationListener(final PayloadChannelIdentifier oldIdentifier, final PayloadChannelIdentifier newIdentifier) {
        this.oldIdentifier = oldIdentifier;
        this.newIdentifier = newIdentifier;
    }
    
    public AbstractPayloadTranslationListener(final Protocol protocol) {
        this.oldIdentifier = protocol.getLegacyIdentifier();
        this.newIdentifier = protocol.getIdentifier();
    }
    
    public abstract byte[] translateIncomingPayload(final byte[] p0);
    
    public abstract <T extends Packet> byte[] translateOutgoingPayload(final T p0);
    
    @ApiStatus.Internal
    public byte[] readIncomingPayload(final byte[] payload) {
        return this.translateIncomingPayload(payload);
    }
    
    @ApiStatus.Internal
    public byte[] readOutgoingPayload(final PayloadChannelIdentifier identifier, final byte[] payload) {
        final Packet packet = ProtocolApiBridge.getProtocolApi().getProtocolService().readPayload(identifier, payload);
        return this.translateOutgoingPayload(packet);
    }
    
    public PayloadChannelIdentifier getOldIdentifier() {
        return this.oldIdentifier;
    }
    
    public PayloadChannelIdentifier getNewIdentifier() {
        return this.newIdentifier;
    }
    
    public <T extends Packet> byte[] writePacketBinary(final T packet) {
        return ProtocolApiBridge.getProtocolApi().getProtocolService().writePacketToBinary(packet);
    }
    
    public byte[] modifyOutgoingPayload(final byte[] payload) {
        return payload;
    }
}
