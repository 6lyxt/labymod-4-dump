// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.models.addon.info.AddonEntrypoint;

public class AddonEntrypointTypeAdapter extends LabyGsonTypeAdapter<AddonEntrypoint>
{
    public AddonEntrypoint deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new AddonEntrypoint(json.getAsString(), null, 1000);
        }
        final JsonObject object = json.getAsJsonObject();
        return new AddonEntrypoint(object.get("name").getAsString(), object.has("version") ? object.get("version").getAsString() : null, object.get("priority").getAsInt());
    }
    
    public JsonElement serialize(final AddonEntrypoint src, final Type typeOfSrc, final JsonSerializationContext context) {
        return context.serialize((Object)src);
    }
}
