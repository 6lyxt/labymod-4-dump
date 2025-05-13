// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import net.labymod.api.client.chat.autotext.AutoTextService;
import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonArray;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.Laby;
import com.google.gson.JsonElement;
import net.labymod.core.configuration.converter.models.LegacyPlayerMenuEntry;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;

public class LegacyPlayerMenuConverter extends LegacyConverter<JsonObject>
{
    public LegacyPlayerMenuConverter() {
        super("playermenu.json", JsonObject.class);
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        final JsonArray entries = jsonObject.getAsJsonArray("playerMenuEntries");
        final List<LegacyPlayerMenuEntry> legacyPlayerMenuEntries = new ArrayList<LegacyPlayerMenuEntry>();
        final Iterator iterator = entries.iterator();
        JsonElement entry = null;
        while (iterator.hasNext()) {
            entry = (JsonElement)iterator.next();
            legacyPlayerMenuEntries.add(this.fromJson(entry, (Class<? extends LegacyPlayerMenuEntry>)LegacyPlayerMenuEntry.class));
        }
        final AutoTextService autoTextService = Laby.references().autoTextService();
        for (final AutoTextEntry entry2 : autoTextService.getEntries()) {
            if (!entry2.displayInInteractionMenu().get()) {
                continue;
            }
            legacyPlayerMenuEntries.removeIf(e -> entry.displayName().get().equals(e.getDisplayName()));
        }
        for (final LegacyPlayerMenuEntry legacyPlayerMenuEntry : legacyPlayerMenuEntries) {
            autoTextService.addEntry(legacyPlayerMenuEntry.convert());
        }
    }
    
    @Override
    public boolean hasStuffToConvert() {
        return this.getValue() != null && this.getValue().getAsJsonArray("playerMenuEntries").size() != 0;
    }
}
