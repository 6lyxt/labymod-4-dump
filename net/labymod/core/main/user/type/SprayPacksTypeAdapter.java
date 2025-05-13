// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.type;

import java.util.Iterator;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import net.labymod.core.main.user.shop.spray.model.SprayPacks;
import com.google.gson.JsonDeserializer;

public class SprayPacksTypeAdapter implements JsonDeserializer<SprayPacks>, JsonSerializer<SprayPacks>
{
    public SprayPacks deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final SprayPacks sprayPacks = new SprayPacks();
        if (!json.isJsonObject()) {
            return sprayPacks;
        }
        final JsonObject stickerObject = json.getAsJsonObject();
        if (stickerObject.has("p") && stickerObject.get("p").isJsonArray()) {
            final JsonArray packArray = stickerObject.get("p").getAsJsonArray();
            for (int i = 0; i < packArray.size(); ++i) {
                sprayPacks.getShorts().add(packArray.get(i).getAsShort());
            }
        }
        return sprayPacks;
    }
    
    public JsonElement serialize(final SprayPacks packs, final Type type, final JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        final JsonArray array = new JsonArray();
        for (final Short id : packs.getShorts()) {
            array.add((JsonElement)new JsonPrimitive((Number)id));
        }
        obj.add("p", (JsonElement)array);
        return (JsonElement)obj;
    }
}
