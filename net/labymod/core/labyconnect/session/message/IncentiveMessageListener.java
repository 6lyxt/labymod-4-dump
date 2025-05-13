// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import java.util.Collections;
import net.labymod.core.labyconnect.object.lootbox.LootBoxInventoryItem;
import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonArray;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.core.labyconnect.object.lootbox.content.PoolCategory;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxShopItem;
import java.util.ArrayList;
import java.util.UUID;
import net.labymod.core.labyconnect.object.lootbox.LootBoxService;
import com.google.gson.JsonObject;
import net.labymod.core.main.LabyMod;
import com.google.gson.JsonElement;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.util.logging.Logging;

public class IncentiveMessageListener implements MessageListener
{
    private static final Logging LOGGER;
    private final User self;
    
    public IncentiveMessageListener(final User self) {
        this.self = self;
    }
    
    @Override
    public void listen(final String message) {
        final JsonElement element = (JsonElement)IncentiveMessageListener.GSON.fromJson(message, (Class)JsonElement.class);
        if (element.isJsonObject()) {
            final JsonObject obj = element.getAsJsonObject();
            final String type = obj.get("type").getAsString();
            final LootBoxService service = LabyMod.references().lootBoxService();
            if (type.equals("inventory")) {
                this.updateInventory(service, obj.get("inventory").getAsJsonObject());
            }
            if (type.equals("open")) {
                this.openLootBox(service, obj.get("open").getAsJsonObject());
            }
        }
    }
    
    private void updateInventory(final LootBoxService service, final JsonObject inventoryObject) {
        final boolean available = inventoryObject.has("available");
        final boolean opened = inventoryObject.has("opened");
        IncentiveMessageListener.LOGGER.info("Updating incentive inventory: available: {}, opened: {}", available, opened);
        if (available && opened) {
            final int amountAvailable = inventoryObject.get("available").getAsInt();
            final int amountOpened = inventoryObject.get("opened").getAsInt();
            service.updateInventory(amountAvailable, amountOpened, this.parseLootBoxInventory(service, inventoryObject));
        }
    }
    
    private void openLootBox(final LootBoxService service, final JsonObject openObject) {
        final UUID uniqueId = UUID.fromString(openObject.get("player").getAsString());
        final int duration = openObject.get("duration").getAsInt();
        final int lootBoxType = openObject.has("type") ? openObject.get("type").getAsInt() : Integer.MIN_VALUE;
        if (uniqueId.equals(this.self.getUniqueId())) {
            service.openLootBox(lootBoxType);
        }
        final JsonArray poolArray = openObject.get("pool").getAsJsonArray();
        final List<LootBoxShopItem> pool = new ArrayList<LootBoxShopItem>();
        for (final JsonElement item : poolArray) {
            final JsonObject itemObj = item.getAsJsonObject();
            final short id = itemObj.get("id").getAsShort();
            final String name = itemObj.get("name").getAsString();
            final JsonElement imageUrl = itemObj.get("imageUrl");
            final Integer color = itemObj.has("color") ? Integer.valueOf(itemObj.get("color").getAsInt()) : null;
            final String categoryName = itemObj.get("category").getAsString();
            final String imageUrlString = imageUrl.isJsonNull() ? null : imageUrl.getAsString();
            final PoolCategory category = PoolCategory.valueOf(categoryName);
            pool.add(new LootBoxShopItem(id, name, imageUrlString, color, category));
        }
        final LootBoxContent content = new LootBoxContent(pool, duration);
        ThreadSafe.executeOnRenderThread(() -> service.spawnLootBox(uniqueId, content, lootBoxType));
    }
    
    private List<LootBoxInventoryItem> parseLootBoxInventory(final LootBoxService service, final JsonObject inventory) {
        if (!inventory.has("content")) {
            return Collections.emptyList();
        }
        final List<LootBoxInventoryItem> items = new ArrayList<LootBoxInventoryItem>();
        try {
            final JsonArray content = inventory.getAsJsonArray("content");
            for (final JsonElement entry : content) {
                if (!entry.isJsonObject()) {
                    continue;
                }
                final JsonObject object = entry.getAsJsonObject();
                final int contentType = object.get("type").getAsInt();
                final boolean contentAvailable = object.get("available").getAsBoolean();
                items.add(LootBoxInventoryItem.create(service, contentType, contentAvailable));
            }
        }
        catch (final ClassCastException ex) {}
        return items;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
