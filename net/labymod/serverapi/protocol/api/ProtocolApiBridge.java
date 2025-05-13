// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.api;

@Deprecated(forRemoval = true, since = "4.2.24")
public class ProtocolApiBridge
{
    private static ProtocolApi protocolApi;
    
    public static void initialize(final ProtocolApi protocolApi) {
        ProtocolApiBridge.protocolApi = protocolApi;
    }
    
    public static ProtocolApi getProtocolApi() {
        return ProtocolApiBridge.protocolApi;
    }
}
