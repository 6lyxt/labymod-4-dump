// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.discord;

import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;
import net.labymod.serverapi.core.model.feature.DiscordRPC;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class DiscordRPCTranslationListener extends KeyedTranslationListener
{
    public DiscordRPCTranslationListener() {
        super("discord_rpc");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject object = messageContent.getAsJsonObject();
        boolean hasGame = false;
        String gameMode = null;
        long startTime = 0L;
        long endTime = 0L;
        if (object.has("hasGame")) {
            hasGame = object.get("hasGame").getAsBoolean();
        }
        if (object.has("game_mode")) {
            gameMode = object.get("game_mode").getAsString();
        }
        if (object.has("game_startTime")) {
            startTime = object.get("game_startTime").getAsLong();
        }
        if (object.has("game_endTime")) {
            endTime = object.get("game_endTime").getAsLong();
        }
        if (!hasGame || gameMode == null) {
            return (Packet)new DiscordRPCPacket(DiscordRPC.createReset());
        }
        if (startTime != 0L) {
            return (Packet)new DiscordRPCPacket(DiscordRPC.createWithStart(gameMode, startTime));
        }
        if (endTime != 0L) {
            return (Packet)new DiscordRPCPacket(DiscordRPC.createWithEnd(gameMode, endTime));
        }
        return (Packet)new DiscordRPCPacket(DiscordRPC.create(gameMode));
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
