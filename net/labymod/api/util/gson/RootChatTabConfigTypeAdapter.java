// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;

public class RootChatTabConfigTypeAdapter extends LabyGsonTypeAdapter<RootChatTabConfig.Type>
{
    private static final String IDENTIFIER_KEY = "identifier";
    
    public RootChatTabConfig.Type deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        String typeIdentifier;
        if (json.isJsonObject()) {
            final JsonObject object = json.getAsJsonObject();
            if (object.has("identifier")) {
                typeIdentifier = object.get("identifier").getAsString();
            }
            else {
                typeIdentifier = RootChatTabConfig.Type.CUSTOM.getIdentifier();
            }
        }
        else {
            typeIdentifier = json.getAsString();
        }
        return RootChatTabConfig.Type.of(typeIdentifier);
    }
    
    public JsonElement serialize(final RootChatTabConfig.Type src, final Type typeOfSrc, final JsonSerializationContext context) {
        return (JsonElement)new JsonPrimitive(src.getIdentifier());
    }
}
