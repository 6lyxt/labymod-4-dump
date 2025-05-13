// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListFlagPacket;
import java.util.Locale;
import java.util.UUID;
import net.labymod.serverapi.core.model.display.TabListFlag;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class TablistLanguageFlagTranslationListener extends KeyedTranslationListener
{
    public TablistLanguageFlagTranslationListener() {
        super("language_flag");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        final JsonObject obj = messageContent.getAsJsonObject();
        if (obj.has("users")) {
            final JsonArray array = obj.get("users").getAsJsonArray();
            final List<TabListFlag> flags = new ArrayList<TabListFlag>();
            for (int i = 0; i < array.size(); ++i) {
                final JsonObject userEntry = array.get(i).getAsJsonObject();
                if (userEntry.has("uuid") && userEntry.has("code")) {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(userEntry.get("uuid").getAsString());
                    }
                    catch (final IllegalArgumentException exception) {
                        continue;
                    }
                    final String code = userEntry.get("code").getAsString().toLowerCase(Locale.ROOT);
                    final TabListFlag.TabListFlagCountryCode countryCode = TabListFlag.TabListFlagCountryCode.getCountryCode(code);
                    if (countryCode != null) {
                        flags.add(TabListFlag.create(uuid, countryCode));
                    }
                }
            }
            return (Packet)new TabListFlagPacket((List)flags);
        }
        return null;
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
