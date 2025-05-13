// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.gson;

import com.google.gson.JsonParseException;
import net.labymod.api.util.version.UnknownVersion;
import net.labymod.api.util.version.CalendarVersion;
import net.labymod.api.util.version.SemanticVersion;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonDeserializer;
import net.labymod.api.models.version.Version;
import com.google.gson.JsonSerializer;

public class VersionTypeAdapter implements JsonSerializer<Version>, JsonDeserializer<Version>
{
    public JsonElement serialize(final Version version, final Type typeOfSrc, final JsonSerializationContext context) {
        return (JsonElement)new JsonPrimitive(version.toString());
    }
    
    public Version deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        final String version = json.getAsString();
        if (SemanticVersion.isFormat(version)) {
            return new SemanticVersion(version);
        }
        if (CalendarVersion.isFormat(version)) {
            return new CalendarVersion(version);
        }
        return new UnknownVersion(version);
    }
}
