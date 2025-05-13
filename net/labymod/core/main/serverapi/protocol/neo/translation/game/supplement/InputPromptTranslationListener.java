// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement;

import net.labymod.serverapi.core.packet.serverbound.game.supplement.InputPromptResponsePacket;
import net.labymod.api.client.component.Component;
import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.InputPromptPacket;
import net.labymod.serverapi.core.model.supplement.InputPrompt;
import net.labymod.api.Laby;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class InputPromptTranslationListener extends KeyedTranslationListener
{
    public InputPromptTranslationListener() {
        super("input_prompt");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject object = messageContent.getAsJsonObject();
        int id = -1;
        ServerAPIComponent message = null;
        ServerAPIComponent placeholder = null;
        String defaultValue = null;
        int maxLength = 0;
        if (object.has("id")) {
            id = object.get("id").getAsInt();
        }
        if (object.has("raw_json_text")) {
            final JsonElement rawJsonText = object.get("raw_json_text");
            final Component component = GsonComponentSerializer.gson().deserializeFromTree(rawJsonText);
            message = (ServerAPIComponent)ServerAPIComponent.text(LegacyComponentSerializer.legacySection().serialize(component));
        }
        else if (object.has("message")) {
            final String value = object.get("message").getAsString();
            final String content = Laby.labyAPI().minecraft().componentMapper().translateColorCodes(value);
            message = (ServerAPIComponent)ServerAPIComponent.text(content);
        }
        if (id == -1 || message == null) {
            return null;
        }
        if (object.has("value")) {
            defaultValue = object.get("value").getAsString();
        }
        if (object.has("placeholder")) {
            final String value = object.get("placeholder").getAsString();
            final String content = Laby.labyAPI().minecraft().componentMapper().translateColorCodes(value);
            placeholder = (ServerAPIComponent)ServerAPIComponent.text(content);
        }
        if (object.has("max_length")) {
            maxLength = object.get("max_length").getAsInt();
        }
        final InputPromptPacket subtitlePacket = new InputPromptPacket(InputPrompt.create(message, placeholder, defaultValue, maxLength));
        subtitlePacket.setIdentifier(id);
        return (Packet)subtitlePacket;
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        if (packet instanceof final InputPromptResponsePacket responsePacket) {
            final JsonObject object = new JsonObject();
            object.addProperty("id", (Number)responsePacket.getIdentifier());
            object.addProperty("value", responsePacket.getValue());
            return (JsonElement)object;
        }
        return null;
    }
}
