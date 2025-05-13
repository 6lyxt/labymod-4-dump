// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.type;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.Iterator;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.Comparator;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import java.util.ArrayList;
import net.labymod.core.main.user.GameUserItemData;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.core.main.user.shop.item.ItemService;
import net.labymod.api.util.logging.Logging;
import com.google.gson.JsonSerializer;
import net.labymod.core.main.user.GameUserItem;
import java.util.List;
import com.google.gson.JsonDeserializer;

public class GameUserItemTypeAdapter implements JsonDeserializer<List<GameUserItem>>, JsonSerializer<List<GameUserItem>>
{
    private static final Logging LOGGER;
    private final ItemService itemService;
    
    public GameUserItemTypeAdapter(final ItemService itemService) {
        this.itemService = itemService;
    }
    
    public List<GameUserItem> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final GameUserItemData[] itemOptions = (GameUserItemData[])context.deserialize(json, (Type)GameUserItemData[].class);
        final List<GameUserItem> items = new ArrayList<GameUserItem>();
        final GameUserItemData[] array = itemOptions;
        for (int length = array.length, i = 0; i < length; ++i) {
            final GameUserItemData itemOption = array[i];
            AbstractItem item = this.itemService.getItemById(itemOption.getIdentifier());
            if (item != null) {
                final String[] options = itemOption.getOptions();
                item = item.copy();
                final ItemDetails itemDetails = item.itemDetails();
                final ItemMetadata itemMetadata = new ItemMetadata(itemDetails);
                if (itemDetails.getOptions() != null) {
                    try {
                        ItemMetadataUtil.serialize(itemMetadata, options);
                    }
                    catch (final MetadataException exception) {
                        GameUserItemTypeAdapter.LOGGER.error("Item options for \"{}\" could not be serialized because \"{}\"", itemDetails.getIdentifier() + "/" + itemDetails.getName(), exception.getMessage());
                    }
                }
                items.add(new GameUserItem(item, itemMetadata));
            }
        }
        items.sort(Comparator.comparingInt(value -> value.item().getIdentifier()));
        return items;
    }
    
    public JsonElement serialize(final List<GameUserItem> list, final Type type, final JsonSerializationContext context) {
        final JsonArray array = new JsonArray();
        for (final GameUserItem item : list) {
            try {
                final JsonObject cosmetic = new JsonObject();
                cosmetic.addProperty("i", (Number)item.item().getIdentifier());
                final JsonArray data = new JsonArray();
                final List<Object> out = ItemMetadataUtil.deserialize(item.itemMetadata());
                for (final Object object : out) {
                    data.add(context.serialize(object));
                }
                cosmetic.add("d", (JsonElement)data);
                array.add((JsonElement)cosmetic);
            }
            catch (final MetadataException e) {
                e.printStackTrace();
            }
        }
        return (JsonElement)array;
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("Item Type Adapter");
    }
}
