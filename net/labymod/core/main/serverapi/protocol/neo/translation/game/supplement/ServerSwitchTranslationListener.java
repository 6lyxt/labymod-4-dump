// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.supplement;

import net.labymod.serverapi.core.packet.serverbound.game.supplement.ServerSwitchPromptResponsePacket;
import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.ServerSwitchPromptPacket;
import net.labymod.serverapi.core.model.supplement.ServerSwitchPrompt;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.api.Laby;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class ServerSwitchTranslationListener extends KeyedTranslationListener
{
    public ServerSwitchTranslationListener() {
        super("server_switch");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        final JsonObject object = jsonElement.getAsJsonObject();
        ServerAPIComponent title = null;
        String address = null;
        if (object.has("title")) {
            final String value = object.get("title").getAsString();
            final String content = Laby.labyAPI().minecraft().componentMapper().translateColorCodes(value);
            title = (ServerAPIComponent)ServerAPIComponent.text(content);
        }
        if (object.has("address")) {
            address = object.get("address").getAsString();
        }
        if (title == null || address == null) {
            return null;
        }
        final boolean preview = object.has("preview") && object.get("preview").getAsBoolean();
        return (Packet)new ServerSwitchPromptPacket(ServerSwitchPrompt.create(title, address, preview));
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        if (packet instanceof final ServerSwitchPromptResponsePacket responsePacket) {
            final JsonObject object = new JsonObject();
            object.addProperty("address", responsePacket.getAddress());
            object.addProperty("accepted", Boolean.valueOf(responsePacket.wasAccepted()));
            return (JsonElement)object;
        }
        return null;
    }
}
