// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.util;

import net.labymod.core.main.user.shop.item.AbstractItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.loader.DefaultLabyModLoader;
import com.google.gson.JsonElement;
import net.labymod.api.util.logging.Logging;

public class CosmeticUtil
{
    private static final Logging LOGGER;
    
    public static void addCosmetic(final JsonElement element, final int id) {
        if (!DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment()) {
            return;
        }
        if (!element.isJsonObject()) {
            return;
        }
        final DefaultGameUserService service = (DefaultGameUserService)Laby.references().gameUserService();
        final AbstractItem item = service.itemService().getItemById(id);
        if (item == null) {
            CosmeticUtil.LOGGER.warn("Could not find a cosmetic with id {}", id);
            return;
        }
        final String[] defaultData = item.itemDetails().getDefaultData();
        final JsonObject cosmeticObject = new JsonObject();
        cosmeticObject.addProperty("i", (Number)id);
        final JsonArray options = new JsonArray();
        for (final String data : defaultData) {
            options.add(data);
        }
        cosmeticObject.add("d", (JsonElement)options);
        final JsonObject object = element.getAsJsonObject();
        JsonArray cosmetics = new JsonArray();
        if (!object.has("c")) {
            object.add("c", (JsonElement)cosmetics);
        }
        else {
            cosmetics = object.getAsJsonArray("c");
        }
        cosmetics.add((JsonElement)cosmeticObject);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
