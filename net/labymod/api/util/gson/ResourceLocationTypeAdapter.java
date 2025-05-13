// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.ResourceLocation;

public class ResourceLocationTypeAdapter extends LabyGsonTypeAdapter<ResourceLocation>
{
    private final ResourceLocationFactory factory;
    
    public ResourceLocationTypeAdapter() {
        this.factory = Laby.references().resourceLocationFactory();
    }
    
    public ResourceLocation deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonPrimitive()) {
            return null;
        }
        return this.factory.parse(json.getAsString());
    }
    
    public JsonElement serialize(final ResourceLocation src, final Type typeOfSrc, final JsonSerializationContext context) {
        return (JsonElement)((src != null) ? new JsonPrimitive(src.toString()) : JsonNull.INSTANCE);
    }
}
