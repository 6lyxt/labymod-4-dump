// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget;

public class PostProcessorTargetTypeAdapter extends LabyGsonTypeAdapter<PostProcessorTarget>
{
    public PostProcessorTarget deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new PostProcessorTarget.FullSizeTarget(json.getAsString());
        }
        if (!json.isJsonObject()) {
            throw new JsonParseException("Expected an object");
        }
        final JsonObject object = json.getAsJsonObject();
        if (object.has("width") || object.has("height")) {
            return (PostProcessorTarget)context.deserialize(json, (Type)PostProcessorTarget.CustomSizeTarget.class);
        }
        return (PostProcessorTarget)context.deserialize(json, (Type)PostProcessorTarget.FullSizeTarget.class);
    }
    
    public JsonElement serialize(final PostProcessorTarget src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) {
            return (JsonElement)JsonNull.INSTANCE;
        }
        if (src instanceof PostProcessorTarget.FullSizeTarget) {
            return (JsonElement)new JsonPrimitive(src.getName());
        }
        return context.serialize((Object)src);
    }
}
