// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import net.labymod.core.labyconnect.object.marker.MarkerService;
import com.google.gson.JsonObject;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class MarkerTranslationListener extends KeyedTranslationListener
{
    public MarkerTranslationListener() {
        super("marker");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject object = messageContent.getAsJsonObject();
        final MarkerService markerService = LabyMod.references().markerService();
        if (object.has("send_markers")) {
            MarkerPacket.MarkerSendType type = MarkerPacket.MarkerSendType.LABY_CONNECT;
            if (object.has("send_markers") && object.get("send_markers").getAsBoolean()) {
                type = MarkerPacket.MarkerSendType.SERVER;
            }
            return (Packet)new MarkerPacket(type);
        }
        if (object.has("add_marker") && object.get("add_marker").isJsonObject()) {
            final JsonObject marker = object.getAsJsonObject("add_marker");
            final UUID sender = UUID.fromString(marker.get("sender").getAsString());
            final int x = marker.get("x").getAsInt();
            final int y = marker.get("y").getAsInt();
            final int z = marker.get("z").getAsInt();
            final boolean large = marker.get("large").getAsBoolean();
            final UUID target = marker.has("target") ? UUID.fromString(marker.get("target").getAsString()) : null;
            return (Packet)new AddMarkerPacket(sender, x, y, z, large, target);
        }
        return null;
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
