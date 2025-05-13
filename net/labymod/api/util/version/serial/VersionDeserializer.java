// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version.serial;

import net.labymod.api.util.version.UnknownVersion;
import net.labymod.api.util.version.CalendarVersion;
import net.labymod.api.util.version.SemanticVersion;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.models.version.Version;
import com.google.gson.JsonDeserializer;

public class VersionDeserializer implements JsonDeserializer<Version>
{
    public Version deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final String version = json.getAsString();
        return from(version);
    }
    
    @NotNull
    public static Version from(@NotNull final String version) {
        if (SemanticVersion.isFormat(version)) {
            return new SemanticVersion(version);
        }
        if (CalendarVersion.isFormat(version)) {
            return new CalendarVersion(version);
        }
        return new UnknownVersion(version);
    }
}
