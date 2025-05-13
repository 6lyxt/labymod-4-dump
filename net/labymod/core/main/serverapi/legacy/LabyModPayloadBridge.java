// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.legacy;

import net.labymod.api.Laby;
import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.api.ProtocolApi;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.serverapi.LabyProtocolApi;
import net.labymod.core.main.serverapi.payload.LabyModPayloadChannelIdentifierSerializer;
import net.labymod.serverapi.protocol.payload.PayloadBridge;

@Deprecated(forRemoval = true, since = "4.2.24")
public class LabyModPayloadBridge extends PayloadBridge
{
    private final LabyModPayloadChannelIdentifierSerializer serializer;
    
    public LabyModPayloadBridge(@NotNull final LabyProtocolApi protocolApi) {
        super(protocolApi);
        this.serializer = new LabyModPayloadChannelIdentifierSerializer();
    }
    
    public static net.labymod.serverapi.api.payload.PayloadChannelIdentifier oldToNewIdentifier(final PayloadChannelIdentifier identifier) {
        return net.labymod.serverapi.api.payload.PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath());
    }
    
    public static PayloadChannelIdentifier newToOldIdentifier(final net.labymod.serverapi.api.payload.PayloadChannelIdentifier identifier) {
        return PayloadChannelIdentifier.create(identifier.getNamespace(), identifier.getPath());
    }
    
    @Override
    public void sendPayload(final PayloadChannelIdentifier payloadChannelIdentifier, final byte[] payload) {
        Laby.labyAPI().serverController().sendPayload(this.serializer.serialize(oldToNewIdentifier(payloadChannelIdentifier)), payload);
    }
}
