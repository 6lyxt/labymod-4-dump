// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload;

import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import org.jetbrains.annotations.NotNull;
import net.labymod.serverapi.protocol.api.ProtocolApi;
import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;

@Deprecated(forRemoval = true, since = "4.2.24")
public abstract class PayloadBridge
{
    protected final ProtocolService protocolService;
    
    public PayloadBridge(@NotNull final ProtocolApi protocolApi) {
        this.protocolService = protocolApi.getProtocolService();
    }
    
    public abstract void sendPayload(final PayloadChannelIdentifier p0, final byte[] p1);
}
