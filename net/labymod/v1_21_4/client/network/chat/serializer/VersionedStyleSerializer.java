// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.network.chat.serializer;

import com.google.gson.JsonSerializationContext;
import java.util.function.Function;
import com.google.gson.JsonParseException;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import net.labymod.api.client.component.serializer.Serializer;

public class VersionedStyleSerializer implements Serializer, JsonDeserializer<xm>, JsonSerializer<xm>
{
    public xm deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return (xm)xm.b.b.parse((DynamicOps)JsonOps.INSTANCE, (Object)json).getOrThrow((Function)JsonParseException::new);
    }
    
    public JsonElement serialize(final xm src, final Type typeOfSrc, final JsonSerializationContext context) {
        return (JsonElement)xm.b.b.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)src).getOrThrow((Function)JsonParseException::new);
    }
}
