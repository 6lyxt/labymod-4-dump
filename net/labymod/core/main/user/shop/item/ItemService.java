// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

import java.util.function.Consumer;
import java.util.Iterator;
import com.google.gson.JsonObject;
import net.labymod.core.main.user.shop.item.items.legacy.partner.SnoxhItem;
import net.labymod.core.main.user.shop.item.items.legacy.head.EyelidsItem;
import net.labymod.core.main.user.shop.item.model.type.ItemType;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.core.main.user.shop.item.model.AttachmentPosition;
import net.labymod.api.util.GsonUtil;
import java.util.Map;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import java.util.concurrent.Executors;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import java.util.concurrent.ExecutorService;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectRegistry;
import net.labymod.core.util.ArrayIndex;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.service.Service;

public class ItemService extends Service
{
    private static final Logging LOGGER;
    private static final String[] EYE_LIDS_OPTIONS;
    private static final String[] SNOXH_EYES_OPTIONS;
    private final ArrayIndex<AbstractItem> items;
    protected final GeometryEffectRegistry effectRegistry;
    private final ExecutorService itemExecutorService;
    private final Request<JsonElement> cosmeticDataRequest;
    
    public ItemService() {
        this.items = new ArrayIndex<AbstractItem>(256, AbstractItem[]::new);
        this.effectRegistry = new GeometryEffectRegistry();
        this.itemExecutorService = Executors.newWorkStealingPool(Math.max(1, 4));
        this.cosmeticDataRequest = Request.ofGson(JsonElement.class).url(Constants.LegacyUrls.COSMETICS_DATA, new Object[0]).async(false);
    }
    
    @Override
    protected void prepare() {
        this.cosmeticDataRequest.execute(this::readData);
    }
    
    @Override
    public void onServiceUnload() {
        this.items.forEach(item -> {
            if (item == null) {
                return;
            }
            else {
                final Model model = item.getModel();
                if (model == null) {
                    return;
                }
                else {
                    final RenderedBuffer renderedBuffer = model.getRenderedBuffer();
                    if (renderedBuffer != null) {
                        model.getRenderedBuffer().dispose();
                    }
                    return;
                }
            }
        });
        this.items.clear();
    }
    
    public AbstractItem getItemById(final int identifier) {
        return this.items.get(identifier);
    }
    
    public GeometryEffectRegistry effectService() {
        return this.effectRegistry;
    }
    
    public ExecutorService getItemExecutorService() {
        return this.itemExecutorService;
    }
    
    private void readData(final Response<JsonElement> response) {
        final JsonElement element = response.getNullable();
        if (element == null || !element.isJsonObject()) {
            return;
        }
        final JsonObject jsonObject = element.getAsJsonObject();
        if (jsonObject.has("cosmetics") && jsonObject.get("cosmetics").isJsonObject()) {
            final JsonObject cosmeticsObject = jsonObject.get("cosmetics").getAsJsonObject();
            int capacity = 0;
            for (final Map.Entry<String, JsonElement> entry : cosmeticsObject.entrySet()) {
                final JsonElement value = entry.getValue();
                if (!value.isJsonObject()) {
                    continue;
                }
                final JsonObject cosmeticObject = value.getAsJsonObject();
                if (!cosmeticObject.has("id") || !cosmeticObject.get("id").isJsonPrimitive()) {
                    continue;
                }
                final int id = cosmeticObject.get("id").getAsInt();
                if (id <= capacity) {
                    continue;
                }
                capacity = id;
            }
            ++capacity;
            this.items.grow(capacity);
            int listId = 0;
            for (final Map.Entry<String, JsonElement> entry2 : cosmeticsObject.entrySet()) {
                final JsonElement cosmeticElement = entry2.getValue();
                if (!cosmeticElement.isJsonObject()) {
                    continue;
                }
                final ItemDetails itemDetails = (ItemDetails)GsonUtil.DEFAULT_GSON.fromJson(cosmeticElement, (Class)ItemDetails.class);
                final AbstractItem item = this.createItem(listId, itemDetails);
                if (item == null) {
                    continue;
                }
                if (itemDetails.getName().contains("Cloak")) {
                    itemDetails.setHiddenWhileWearingElytra(true);
                }
                this.items.set(itemDetails.getIdentifier(), item);
                ++listId;
            }
            capacity += 2;
            this.items.grow(capacity);
            this.registerLegacyItem(listId, details -> {
                details.setIdentifier(36);
                details.setOptions(ItemService.EYE_LIDS_OPTIONS);
                details.setPosition(AttachmentPosition.FACE);
                details.setAttachmentPoint(AttachmentPoint.HEAD);
                details.setType(ItemType.COSMETIC);
                details.setName("Eyelids");
                details.setDraft(false);
                return;
            }, EyelidsItem::new);
            ++listId;
            this.registerLegacyItem(listId, details -> {
                details.setIdentifier(32);
                details.setOptions(ItemService.SNOXH_EYES_OPTIONS);
                details.setPosition(AttachmentPosition.FACE);
                details.setAttachmentPoint(AttachmentPoint.HEAD);
                details.setType(ItemType.COSMETIC);
                details.setName("Snoxh Eyes");
                details.setDraft(false);
            }, SnoxhItem::new);
        }
    }
    
    private AbstractItem createItem(final int listId, final ItemDetails details) {
        final ItemType type = details.getType();
        try {
            return type.produce(listId, details);
        }
        catch (final IllegalStateException exception) {
            ItemService.LOGGER.error("Failed to create item with identifier {} and type {}", details.getIdentifier(), type, exception);
            return null;
        }
    }
    
    private <T extends AbstractItem> void registerLegacyItem(final int listId, final Consumer<ItemDetails> detailsConsumer, final ItemFactory itemFactory) {
        final ItemDetails details = new ItemDetails();
        detailsConsumer.accept(details);
        final int cosmeticId = details.getIdentifier();
        if (this.items.get(cosmeticId) != null) {
            return;
        }
        this.items.set(cosmeticId, itemFactory.apply(listId, details));
    }
    
    static {
        LOGGER = Logging.getLogger();
        EYE_LIDS_OPTIONS = new String[] { "size", "rgb", "can_blink", "can_sleep" };
        SNOXH_EYES_OPTIONS = new String[] { "size", "left_visible", "right_visible", "rgb", "brightness" };
    }
    
    @FunctionalInterface
    private interface ItemFactory
    {
        AbstractItem apply(final int p0, final ItemDetails p1);
    }
}
