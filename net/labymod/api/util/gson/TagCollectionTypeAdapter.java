// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;

public class TagCollectionTypeAdapter extends LabyGsonTypeAdapter<TagInputWidget.TagCollection>
{
    public TagInputWidget.TagCollection deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonArray()) {
            throw new JsonParseException("Tag Collection element is not a json array!");
        }
        final TagInputWidget.TagCollection tagCollection = new TagInputWidget.TagCollection();
        final JsonArray jsonArray = json.getAsJsonArray();
        for (final JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonPrimitive()) {
                final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (!jsonPrimitive.isString()) {
                    continue;
                }
                final String asString = jsonPrimitive.getAsString();
                if (asString.trim().isEmpty()) {
                    continue;
                }
                tagCollection.add(asString);
            }
        }
        return tagCollection;
    }
    
    public JsonElement serialize(final TagInputWidget.TagCollection src, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonArray jsonArray = new JsonArray();
        for (final TagInputWidget.TagCollection.Tag tag : src.getTags()) {
            final String content = tag.getContent();
            if (!content.trim().isEmpty()) {
                jsonArray.add((JsonElement)new JsonPrimitive(content));
            }
        }
        return (JsonElement)jsonArray;
    }
}
