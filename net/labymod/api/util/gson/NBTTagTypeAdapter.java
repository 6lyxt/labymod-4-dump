// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import java.util.Map;
import net.labymod.api.nbt.NBTTagType;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.util.Iterator;
import net.labymod.api.nbt.tags.NBTTagList;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import net.labymod.api.nbt.tags.NBTTagCompound;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.Laby;
import net.labymod.api.nbt.NBTFactory;
import com.google.gson.JsonDeserializer;
import net.labymod.api.nbt.NBTTag;
import com.google.gson.JsonSerializer;

public class NBTTagTypeAdapter implements JsonSerializer<NBTTag<?>>, JsonDeserializer<NBTTag<?>>
{
    private final NBTFactory nbtFactory;
    
    public NBTTagTypeAdapter() {
        this.nbtFactory = Laby.references().nbtFactory();
    }
    
    public JsonElement serialize(final NBTTag tag, final Type type, final JsonSerializationContext context) {
        return (JsonElement)this.toJsonObject(tag, context);
    }
    
    private JsonObject toJsonObject(final NBTTag<?> tag, final JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("type", tag.type().name());
        obj.add("value", this.toJsonElementValue(tag, context));
        return obj;
    }
    
    private JsonElement toJsonElementValue(final NBTTag<?> tag, final JsonSerializationContext context) {
        final Object value = tag.value();
        if (value == null) {
            return (JsonElement)JsonNull.INSTANCE;
        }
        switch (tag.type()) {
            case COMPOUND: {
                final JsonObject object = new JsonObject();
                final NBTTagCompound compound = (NBTTagCompound)tag;
                for (final String key : compound.keySet()) {
                    final NBTTag<?> entryValue = compound.get(key);
                    if (entryValue != null) {
                        object.add(key, (JsonElement)this.toJsonObject(entryValue, context));
                    }
                }
                return (JsonElement)object;
            }
            case BYTE_ARRAY: {
                final byte[] array = (byte[])value;
                final JsonArray jsonArray = new JsonArray();
                for (final byte b : array) {
                    jsonArray.add((JsonElement)new JsonPrimitive((Number)b));
                }
                return (JsonElement)jsonArray;
            }
            case INT_ARRAY: {
                final int[] array2 = (int[])value;
                final JsonArray jsonArray = new JsonArray();
                for (final int i : array2) {
                    jsonArray.add((JsonElement)new JsonPrimitive((Number)i));
                }
                return (JsonElement)jsonArray;
            }
            case LONG_ARRAY: {
                final long[] array3 = (long[])value;
                final JsonArray jsonArray = new JsonArray();
                for (final long l : array3) {
                    jsonArray.add((JsonElement)new JsonPrimitive((Number)l));
                }
                return (JsonElement)jsonArray;
            }
            case LIST: {
                final JsonArray jsonArray2 = new JsonArray();
                final NBTTagList list = (NBTTagList)tag;
                for (int j = 0; j < list.size(); ++j) {
                    final NBTTag<?> entryValue2 = list.get(j);
                    if (entryValue2 != null) {
                        jsonArray2.add((JsonElement)this.toJsonObject(entryValue2, context));
                    }
                }
                return (JsonElement)jsonArray2;
            }
            default: {
                return context.serialize((Object)tag.value());
            }
        }
    }
    
    public NBTTag<?> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        return this.fromJsonObject(json.getAsJsonObject(), context);
    }
    
    private NBTTag<?> fromJsonObject(final JsonObject element, final JsonDeserializationContext context) {
        final JsonElement value = element.get("value");
        if (value.isJsonNull()) {
            return null;
        }
        final NBTTagType type = NBTTagType.valueOf(element.get("type").getAsString());
        switch (type) {
            case COMPOUND: {
                final NBTTagCompound compound = this.nbtFactory.compound();
                final JsonObject obj = value.getAsJsonObject();
                for (final Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                    final String key = entry.getKey();
                    final JsonObject objValue = obj.get(key).getAsJsonObject();
                    final NBTTag<?> tag = this.fromJsonObject(objValue, context);
                    if (tag != null) {
                        compound.set(key, tag);
                    }
                }
                return compound;
            }
            case BYTE_ARRAY: {
                final JsonArray array = value.getAsJsonArray();
                final byte[] bytes = new byte[array.size()];
                for (int i = 0; i < array.size(); ++i) {
                    bytes[i] = array.get(i).getAsByte();
                }
                return this.nbtFactory.create(bytes);
            }
            case INT_ARRAY: {
                final JsonArray array = value.getAsJsonArray();
                final int[] ints = new int[array.size()];
                for (int i = 0; i < array.size(); ++i) {
                    ints[i] = array.get(i).getAsInt();
                }
                return this.nbtFactory.create(ints);
            }
            case LONG_ARRAY: {
                final JsonArray array = value.getAsJsonArray();
                final long[] longs = new long[array.size()];
                for (int i = 0; i < array.size(); ++i) {
                    longs[i] = array.get(i).getAsLong();
                }
                return this.nbtFactory.create(longs);
            }
            case STRING: {
                return this.nbtFactory.create(value.getAsString());
            }
            case BYTE: {
                return this.nbtFactory.create(value.getAsByte());
            }
            case SHORT: {
                return this.nbtFactory.create(value.getAsShort());
            }
            case INT:
            case ANY_NUMERIC: {
                return this.nbtFactory.create(value.getAsInt());
            }
            case LONG: {
                return this.nbtFactory.create(value.getAsLong());
            }
            case FLOAT: {
                return this.nbtFactory.create(value.getAsFloat());
            }
            case DOUBLE: {
                return this.nbtFactory.create(value.getAsDouble());
            }
            case LIST: {
                final JsonArray array = value.getAsJsonArray();
                final NBTTagList list = this.nbtFactory.list();
                for (int i = 0; i < array.size(); ++i) {
                    final JsonObject objValue2 = array.get(i).getAsJsonObject();
                    final NBTTag<?> tag2 = this.fromJsonObject(objValue2, context);
                    if (tag2 != null) {
                        list.add(tag2);
                    }
                }
                return list;
            }
            default: {
                return null;
            }
        }
    }
}
