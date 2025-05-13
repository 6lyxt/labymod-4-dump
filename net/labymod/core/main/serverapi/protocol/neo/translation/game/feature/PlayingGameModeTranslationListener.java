// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.feature.PlayingGameModePacket;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class PlayingGameModeTranslationListener extends KeyedTranslationListener
{
    public PlayingGameModeTranslationListener() {
        super("server_gamemode");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        boolean visible = false;
        String gameMode = null;
        if (jsonObject.has("show_gamemode")) {
            visible = jsonObject.get("show_gamemode").getAsBoolean();
        }
        if (jsonObject.has("gamemode_name")) {
            gameMode = jsonObject.get("gamemode_name").getAsString();
        }
        return (Packet)new PlayingGameModePacket(visible ? gameMode : null);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
