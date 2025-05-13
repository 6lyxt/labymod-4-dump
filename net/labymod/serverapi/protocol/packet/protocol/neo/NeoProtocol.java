// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet.protocol.neo;

import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;
import net.labymod.serverapi.protocol.packet.protocol.Protocol;

@Deprecated(forRemoval = true, since = "4.2.24")
public class NeoProtocol extends Protocol
{
    private static final PayloadChannelIdentifier NEO;
    private static final PayloadChannelIdentifier LABYMOD_3_MAIN;
    
    public NeoProtocol() {
        super(NeoProtocol.NEO, NeoProtocol.LABYMOD_3_MAIN);
    }
    
    static {
        NEO = PayloadChannelIdentifier.create("labymod", "neo");
        LABYMOD_3_MAIN = PayloadChannelIdentifier.create("labymod3", "main");
    }
}
