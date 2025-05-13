// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.moderation;

import net.labymod.serverapi.core.packet.serverbound.game.moderation.AddonRecommendationResponsePacket;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonRecommendationPacket;
import net.labymod.serverapi.core.model.moderation.RecommendedAddon;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class AddonRecommendationTranslationListener extends KeyedTranslationListener
{
    public AddonRecommendationTranslationListener() {
        super("addon_recommendation");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement jsonElement) {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has("addons")) {
            return null;
        }
        final JsonElement addonsElement = jsonObject.get("addons");
        if (!addonsElement.isJsonArray()) {
            return null;
        }
        final List<RecommendedAddon> recommendedAddons = new ArrayList<RecommendedAddon>();
        final JsonArray addonsArray = addonsElement.getAsJsonArray();
        for (final JsonElement addonElement : addonsArray) {
            if (!addonElement.isJsonObject()) {
                continue;
            }
            final JsonObject addonObject = addonElement.getAsJsonObject();
            if (!addonObject.has("namespace")) {
                continue;
            }
            final String namespace = addonObject.get("namespace").getAsString();
            recommendedAddons.add(RecommendedAddon.of(namespace, addonObject.has("required") && addonObject.get("required").getAsBoolean()));
        }
        if (recommendedAddons.isEmpty()) {
            return null;
        }
        return (Packet)new AddonRecommendationPacket((List)recommendedAddons);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        if (packet instanceof final AddonRecommendationResponsePacket responsePacket) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("gui_closed", Boolean.valueOf(!responsePacket.isInitial()));
            jsonObject.addProperty("all_installed", Boolean.valueOf(responsePacket.isAllInstalled()));
            final JsonArray missingArray = new JsonArray();
            for (final String namespace : responsePacket.getMissingAddons()) {
                final JsonObject missingObject = new JsonObject();
                missingObject.addProperty("namespace", namespace);
                missingArray.add((JsonElement)missingObject);
            }
            jsonObject.add("missing", (JsonElement)missingArray);
            return (JsonElement)jsonObject;
        }
        return null;
    }
}
