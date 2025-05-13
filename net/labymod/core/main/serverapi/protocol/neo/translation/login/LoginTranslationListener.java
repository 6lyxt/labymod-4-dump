// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.login;

import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.serverbound.login.VersionLoginPacket;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class LoginTranslationListener extends KeyedTranslationListener
{
    public LoginTranslationListener() {
        super("INFO");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement jsonElement) {
        return null;
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        if (packet instanceof final VersionLoginPacket versionLoginPacket) {
            final JsonObject object = new JsonObject();
            object.addProperty("version", versionLoginPacket.getVersion());
            return (JsonElement)object;
        }
        return null;
    }
}
