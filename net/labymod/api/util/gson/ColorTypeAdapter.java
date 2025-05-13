// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.util.Color;

public class ColorTypeAdapter extends LabyGsonTypeAdapter<Color>
{
    private static final String VALUE_KEY = "value";
    private static final String CHROMA_KEY = "chroma";
    private static final String CHROMA_SPEED_KEY = "chromaSpeed";
    
    public Color deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return Color.of(primitive.getAsInt());
            }
        }
        if (!json.isJsonObject()) {
            throw new JsonSyntaxException("Expected color to be a number or json object, but was " + this.getType(json));
        }
        final JsonObject colorObject = json.getAsJsonObject();
        if (!colorObject.has("value")) {
            throw new JsonSyntaxException("Expected color to have a value");
        }
        final JsonElement valueElement = colorObject.get("value");
        if (!valueElement.isJsonPrimitive()) {
            throw new JsonSyntaxException("Expected color value to be a number, but was " + this.getType(valueElement));
        }
        final JsonPrimitive valuePrimitive = valueElement.getAsJsonPrimitive();
        if (!valuePrimitive.isNumber()) {
            throw new JsonSyntaxException("Expected color value to be a number, but was " + this.getType(valueElement));
        }
        final Color color = Color.of(valuePrimitive.getAsInt());
        Float speed = null;
        if (colorObject.has("chromaSpeed")) {
            final JsonElement chromaSpeedElement = colorObject.get("chromaSpeed");
            if (chromaSpeedElement.isJsonPrimitive()) {
                final JsonPrimitive chromaSpeedPrimitive = chromaSpeedElement.getAsJsonPrimitive();
                if (chromaSpeedPrimitive.isNumber()) {
                    speed = chromaSpeedPrimitive.getAsFloat();
                }
            }
        }
        if (colorObject.has("chroma")) {
            final JsonElement chromaElement = colorObject.get("chroma");
            if (chromaElement.isJsonPrimitive()) {
                final JsonPrimitive chromaPrimitive = chromaElement.getAsJsonPrimitive();
                if (chromaPrimitive.isBoolean() && chromaPrimitive.getAsBoolean()) {
                    return (speed != null) ? color.withChroma(speed) : color.withChroma();
                }
            }
        }
        return (speed != null) ? color.withoutChroma(speed) : color;
    }
    
    public JsonElement serialize(final Color color, final Type typeOfSrc, final JsonSerializationContext context) {
        final boolean chroma = color.isChroma();
        final boolean defaultChromaSpeed = color.isDefaultChromaSpeed();
        if (chroma || !defaultChromaSpeed) {
            final JsonObject colorObject = new JsonObject();
            colorObject.addProperty("value", (Number)color.withoutChroma().get());
            if (chroma) {
                colorObject.addProperty("chroma", Boolean.valueOf(true));
            }
            if (!defaultChromaSpeed) {
                colorObject.addProperty("chromaSpeed", (Number)color.getChromaSpeed());
            }
            return (JsonElement)colorObject;
        }
        return (JsonElement)new JsonPrimitive((Number)color.get());
    }
    
    private String getType(final JsonElement element) {
        if (element.isJsonPrimitive()) {
            final JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return "boolean";
            }
            if (primitive.isString()) {
                return "string";
            }
            if (primitive.isNumber()) {
                return "number";
            }
            return "unknown";
        }
        else {
            if (element.isJsonArray()) {
                return "array";
            }
            if (element.isJsonObject()) {
                return "object";
            }
            if (element.isJsonNull()) {
                return "null";
            }
            return "unknown";
        }
    }
}
