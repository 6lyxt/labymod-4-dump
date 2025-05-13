// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.client.gui.screen.key.Key;

public class KeyTypeAdapter extends LabyGsonTypeAdapter<Key>
{
    public Key deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (json.getAsJsonPrimitive().isNumber()) {
            return KeyMapper.getKey(json.getAsInt());
        }
        return KeyMapper.getKey(json.getAsString());
    }
    
    public JsonElement serialize(final Key key, final Type type, final JsonSerializationContext context) {
        return (JsonElement)new JsonPrimitive(key.getActualName());
    }
}
