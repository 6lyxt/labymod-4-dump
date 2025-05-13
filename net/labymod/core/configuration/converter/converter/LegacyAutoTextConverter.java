// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import net.labymod.api.client.chat.autotext.AutoTextService;
import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonArray;
import java.util.Arrays;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import com.google.gson.JsonElement;
import net.labymod.core.configuration.converter.models.LegacyAutoText;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;

public class LegacyAutoTextConverter extends LegacyConverter<JsonObject>
{
    public LegacyAutoTextConverter() {
        super("autotext.json", JsonObject.class);
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        final JsonArray keyBinds = jsonObject.getAsJsonArray("autoTextKeyBinds");
        final List<LegacyAutoText> legacyAutoTexts = new ArrayList<LegacyAutoText>();
        for (final JsonElement keyBind : keyBinds) {
            legacyAutoTexts.add(this.fromJson(keyBind, (Class<? extends LegacyAutoText>)LegacyAutoText.class));
        }
        final List<AutoTextEntry> legacyEntries = new ArrayList<AutoTextEntry>();
        for (final LegacyAutoText legacyAutoText : legacyAutoTexts) {
            final AutoTextEntry autoTextEntry = legacyAutoText.convert();
            if (autoTextEntry != null) {
                legacyEntries.add(autoTextEntry);
            }
        }
        final AutoTextService autoTextService = Laby.references().autoTextService();
        for (final AutoTextEntry entry : autoTextService.getEntries()) {
            if (entry.displayInInteractionMenu().get()) {
                continue;
            }
            legacyEntries.removeIf(e -> Arrays.equals(e.requiredKeys().get(), entry.requiredKeys().get()));
        }
        for (final AutoTextEntry legacyEntry : legacyEntries) {
            autoTextService.addEntry(legacyEntry);
        }
    }
    
    @Override
    public boolean hasStuffToConvert() {
        return this.getValue() != null && this.getValue().getAsJsonArray("autoTextKeyBinds").size() != 0;
    }
}
