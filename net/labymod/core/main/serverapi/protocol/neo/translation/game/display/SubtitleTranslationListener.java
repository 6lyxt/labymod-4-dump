// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.display;

import net.labymod.api.client.component.Component;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.display.SubtitlePacket;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.api.Laby;
import java.util.UUID;
import net.labymod.serverapi.core.model.display.Subtitle;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class SubtitleTranslationListener extends KeyedTranslationListener
{
    public SubtitleTranslationListener() {
        super("account_subtitle");
    }
    
    private double calculateNewSize(double oldSize) {
        if (oldSize < 0.8) {
            oldSize = 0.8;
        }
        if (oldSize > 1.6) {
            oldSize = 1.6;
        }
        return oldSize / 1.6;
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonArray()) {
            return null;
        }
        final JsonArray array = messageContent.getAsJsonArray();
        final List<Subtitle> subtitles = new ArrayList<Subtitle>();
        for (int i = 0; i < array.size(); ++i) {
            final JsonElement jsonElement = array.get(i);
            if (jsonElement.isJsonObject()) {
                final JsonObject object = jsonElement.getAsJsonObject();
                UUID uniqueId = null;
                double size = 1.0;
                ServerAPIComponent text = null;
                if (object.has("uuid")) {
                    uniqueId = UUID.fromString(object.get("uuid").getAsString());
                }
                if (object.has("size")) {
                    size = object.get("size").getAsDouble();
                    size = this.calculateNewSize(size);
                }
                if (object.has("value")) {
                    final String value = object.get("value").getAsString();
                    final String content = Laby.labyAPI().minecraft().componentMapper().translateColorCodes(value);
                    text = (ServerAPIComponent)ServerAPIComponent.text(content);
                }
                if (object.has("raw_json_text")) {
                    final String rawJsonText = object.get("raw_json_text").getAsString();
                    final Component component = GsonComponentSerializer.gson().deserialize(rawJsonText);
                    text = (ServerAPIComponent)ServerAPIComponent.text(LegacyComponentSerializer.legacySection().serialize(component));
                }
                if (uniqueId != null) {
                    subtitles.add(Subtitle.create(uniqueId, text, size));
                }
            }
        }
        return (Packet)new SubtitlePacket((List)subtitles);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
