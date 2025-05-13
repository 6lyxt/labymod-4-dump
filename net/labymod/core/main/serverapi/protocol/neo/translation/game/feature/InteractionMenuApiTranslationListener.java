// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import java.util.Locale;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;
import java.util.ArrayList;
import net.labymod.serverapi.api.packet.Packet;
import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;

public class InteractionMenuApiTranslationListener extends KeyedTranslationListener
{
    public InteractionMenuApiTranslationListener() {
        super("user_menu_actions");
    }
    
    @Override
    protected Packet translateIncomingMessage(final JsonElement messageContent) {
        if (!messageContent.isJsonArray()) {
            return null;
        }
        final List<InteractionMenuEntry> entries = new ArrayList<InteractionMenuEntry>();
        for (final JsonElement entry : messageContent.getAsJsonArray()) {
            if (!entry.isJsonObject()) {
                continue;
            }
            final JsonObject entryObject = entry.getAsJsonObject();
            InteractionMenuEntry.InteractionMenuType type;
            try {
                type = InteractionMenuEntry.InteractionMenuType.fromString(entryObject.get("type").getAsString().toUpperCase(Locale.ENGLISH));
            }
            catch (final Exception exception) {
                continue;
            }
            entries.add(InteractionMenuEntry.create((ServerAPIComponent)ServerAPIComponent.text(entryObject.get("displayName").getAsString()), type, entryObject.get("value").getAsString()));
        }
        return (Packet)new InteractionMenuPacket((List)entries);
    }
    
    @Override
    protected JsonElement translateOutgoingMessage(final Packet packet) {
        return null;
    }
}
