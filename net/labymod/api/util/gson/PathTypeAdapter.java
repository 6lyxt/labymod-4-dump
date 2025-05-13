// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import java.nio.file.Paths;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import java.nio.file.Path;

public class PathTypeAdapter extends LabyGsonTypeAdapter<Path>
{
    public Path deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) {
            return null;
        }
        if (!json.isJsonPrimitive()) {
            throw new JsonParseException("Json cannot be parsed to a path: " + String.valueOf(json));
        }
        return Paths.get(json.getAsString(), new String[0]);
    }
    
    public JsonElement serialize(final Path src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) {
            return (JsonElement)JsonNull.INSTANCE;
        }
        return (JsonElement)new JsonPrimitive(src.toString());
    }
}
