// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import java.util.Iterator;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.component.data.DataComponentKey;
import com.google.gson.JsonParseException;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.Laby;
import com.google.gson.JsonDeserializationContext;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.platform.PlatformEnvironment;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.util.logging.Logging;
import com.google.gson.JsonDeserializer;
import net.labymod.api.client.world.item.ItemStack;
import com.google.gson.JsonSerializer;

@Deprecated(forRemoval = true, since = "4.1.23")
public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack>
{
    private static final Logging LOGGER;
    private static final String COMPONENT_KEY = "nbt";
    
    public JsonElement serialize(final ItemStack itemStack, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        final ResourceLocation identifier = itemStack.getIdentifier();
        obj.addProperty("namespace", identifier.getNamespace());
        obj.addProperty("path", identifier.getPath());
        final DataComponentContainer container = itemStack.getDataComponentContainer();
        if (!PlatformEnvironment.ITEM_COMPONENTS && !container.isEmpty()) {
            try {
                obj.add("nbt", context.serialize((Object)this.asCompound(container)));
            }
            catch (final Exception exception) {
                ItemStackTypeAdapter.LOGGER.warn("Failed to serialize ItemStack", exception);
            }
        }
        final int legacyItemData = itemStack.getLegacyItemData();
        if (legacyItemData != -1) {
            obj.addProperty("legacy_data", (Number)legacyItemData);
        }
        return (JsonElement)obj;
    }
    
    public ItemStack deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = json.getAsJsonObject();
        final String namespace = obj.get("namespace").getAsString();
        final String path = obj.get("path").getAsString();
        final ItemStack itemStack = Laby.references().itemStackFactory().create(namespace, path);
        if (!PlatformEnvironment.ITEM_COMPONENTS && obj.has("nbt")) {
            try {
                final JsonObject nbt = obj.get("nbt").getAsJsonObject();
                final NBTTagCompound tag = (NBTTagCompound)context.deserialize((JsonElement)nbt, (Type)NBTTagCompound.class);
                final NbtDataComponentContainer components = new NbtDataComponentContainer(tag);
                itemStack.applyPatchToDataComponent(DataComponentPatch.createPatch(components));
            }
            catch (final Exception exception) {
                ItemStackTypeAdapter.LOGGER.warn("Failed to deserialize ItemStack", exception);
            }
        }
        if (obj.has("legacy_data")) {
            itemStack.setLegacyItemData(obj.get("legacy_data").getAsInt());
        }
        return itemStack;
    }
    
    private NBTTagCompound asCompound(final DataComponentContainer container) {
        final NBTTagCompound compound = Laby.references().nbtFactory().compound();
        for (final DataComponentKey dataComponentKey : container.keySet()) {
            final Object object = container.get(dataComponentKey);
            compound.set(dataComponentKey.name(), (NBTTag<?>)object);
        }
        return compound;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
