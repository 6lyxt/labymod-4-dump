// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.api;

import net.labymod.serverapi.protocol.packet.protocol.neo.NeoProtocol;
import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;
import net.labymod.serverapi.protocol.payload.PayloadBridge;

@Deprecated(forRemoval = true, since = "4.2.24")
public interface ProtocolApi
{
    PayloadBridge getPayloadBridge();
    
    @Deprecated(forRemoval = true, since = "4.2.24")
    ProtocolService getProtocolService();
    
    ProtocolPlatformLogger getPlatformLogger();
    
    @Deprecated(forRemoval = true, since = "4.2.24")
    default NeoProtocol getNeoProtocol() {
        return this.getProtocolService().getNeoProtocol();
    }
}
