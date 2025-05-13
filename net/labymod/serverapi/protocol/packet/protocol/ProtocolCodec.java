// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol;

import java.util.Iterator;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.serverapi.protocol.packet.Packet;
import java.util.List;

@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolCodec
{
    private final List<Decoder<? extends Packet>> decoderLookup;
    private final ProtocolRegistry registry;
    
    public ProtocolCodec(final ProtocolRegistry registry) {
        this.decoderLookup = new ArrayList<Decoder<? extends Packet>>();
        this.registry = registry;
        this.decoderLookup.add((id, binaryPayload) -> this.decode(this.registry.getAddonProtocols(), id, binaryPayload));
        this.decoderLookup.add((id, binaryPayload) -> this.decode(this.registry.getCustomProtocols(), id, binaryPayload));
    }
    
    public <T extends Packet> T decode(final PayloadChannelIdentifier id, final byte[] binaryPayload, final Decoder<T> defaultDecoder) {
        Packet decodedPacket = defaultDecoder.decode(id, binaryPayload);
        if (decodedPacket == null) {
            for (final Decoder<? extends Packet> decoder : this.decoderLookup) {
                decodedPacket = (Packet)decoder.decode(id, binaryPayload);
                if (decodedPacket != null) {
                    break;
                }
            }
        }
        return (T)decodedPacket;
    }
    
    private <T extends Packet, P extends Protocol> T decode(final Collection<P> protocols, final PayloadChannelIdentifier id, final byte[] binaryPayload) {
        for (final P protocol : protocols) {
            if (protocol.getIdentifier().equals(id)) {
                return (T)protocol.readPacket(binaryPayload);
            }
        }
        return null;
    }
    
    @FunctionalInterface
    public interface Decoder<T extends Packet>
    {
        T decode(final PayloadChannelIdentifier p0, final byte[] p1);
    }
}
