// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.serverapi;

import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;

public abstract class TranslationListener
{
    protected TranslationListener() {
    }
    
    public abstract Packet translateIncomingPayload(final PayloadReader p0);
    
    public abstract PayloadWriter translateOutgoingPayload(final Packet p0);
}
