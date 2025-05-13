// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;
import java.util.UUID;
import net.labymod.serverapi.core.model.feature.Emote;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class EmoteApiTranslationListener extends KeyedTranslationListener
{
    public EmoteApiTranslationListener() {
        super("emote_api");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonArray()) {
            return null;
        }
        final JsonArray emotes = messageContent.getAsJsonArray();
        final List<Emote> entries = new ArrayList<Emote>();
        for (int i = 0; i < emotes.size(); ++i) {
            final JsonElement emoteElement = emotes.get(i);
            if (emoteElement.isJsonObject()) {
                final JsonObject emoteObject = emoteElement.getAsJsonObject();
                final UUID uniqueId = UUID.fromString(emoteObject.get("uuid").getAsString());
                final int emoteId = emoteObject.get("emote_id").getAsInt();
                entries.add(Emote.play(uniqueId, emoteId));
            }
        }
        return (Packet)new EmotePacket((List)entries);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
