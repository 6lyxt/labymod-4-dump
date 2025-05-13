// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.intave.translation;

import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class IntaveConfigTranslationListener extends KeyedTranslationListener
{
    public IntaveConfigTranslationListener() {
        super("clientconfig");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement element) {
        boolean received = false;
        if (element.isJsonPrimitive()) {
            received = element.getAsString().equalsIgnoreCase("received");
        }
        return (Packet)new IntaveClientConfigReceivedPacket(received);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return this.gson.toJsonTree((Object)packet);
    }
}
