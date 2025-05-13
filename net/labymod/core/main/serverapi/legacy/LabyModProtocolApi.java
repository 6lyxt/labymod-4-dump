// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.legacy;

import net.labymod.serverapi.protocol.api.ProtocolApi;
import net.labymod.serverapi.protocol.api.ProtocolApiBridge;
import net.labymod.core.main.serverapi.LabyModProtocolPlatformLogger;
import net.labymod.serverapi.protocol.api.ProtocolPlatformLogger;
import net.labymod.serverapi.protocol.payload.PayloadBridge;
import net.labymod.serverapi.protocol.packet.protocol.ProtocolService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.serverapi.LabyProtocolApi;

@Singleton
@Implements(LabyProtocolApi.class)
@Deprecated(forRemoval = true, since = "4.2.24")
public class LabyModProtocolApi implements LabyProtocolApi
{
    private final ProtocolService protocolService;
    private final PayloadBridge payloadBridge;
    private final ProtocolPlatformLogger protocolPlatformLogger;
    
    public LabyModProtocolApi() {
        this.payloadBridge = new LabyModPayloadBridge(this);
        this.protocolService = new ProtocolService();
        this.protocolPlatformLogger = new LabyModProtocolPlatformLogger();
        ProtocolApiBridge.initialize(this);
        this.protocolService.initialize();
    }
    
    @Override
    public PayloadBridge getPayloadBridge() {
        return this.payloadBridge;
    }
    
    @Override
    public ProtocolService getProtocolService() {
        return this.protocolService;
    }
    
    @Override
    public ProtocolPlatformLogger getPlatformLogger() {
        return this.protocolPlatformLogger;
    }
}
