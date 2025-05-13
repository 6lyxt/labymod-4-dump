// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.property;

import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonPrimitive;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.util.PrimitiveHelper;
import java.lang.reflect.ParameterizedType;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public class ConfigPropertyTypeAdapter implements JsonSerializer<ConfigProperty<?>>, JsonDeserializer<Object>
{
    public JsonElement serialize(final ConfigProperty<?> src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (!(typeOfSrc instanceof ParameterizedType)) {
            return null;
        }
        final ParameterizedType parameterizedType = (ParameterizedType)typeOfSrc;
        if (Reflection.isType(parameterizedType, PrimitiveHelper.BOOLEAN)) {
            return (JsonElement)new JsonPrimitive((Boolean)src.getActualValue());
        }
        if (Reflection.isType(parameterizedType, PrimitiveHelper.NUMBER_PRIMITIVES)) {
            return (JsonElement)new JsonPrimitive((Number)src.getActualValue());
        }
        if (Reflection.isType(parameterizedType, String.class)) {
            return (JsonElement)new JsonPrimitive((String)src.getActualValue());
        }
        if (Reflection.isType(parameterizedType, Character.class)) {
            return (JsonElement)new JsonPrimitive((Character)src.getActualValue());
        }
        return context.serialize((Object)src.getActualValue());
    }
    
    public Object deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        if (type instanceof final ParameterizedType parameterizedType) {
            final Type actualType = parameterizedType.getActualTypeArguments()[0];
            return context.deserialize(json, actualType);
        }
        return null;
    }
}
