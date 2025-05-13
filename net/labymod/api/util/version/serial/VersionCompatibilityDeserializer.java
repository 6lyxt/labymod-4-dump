// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version.serial;

import net.labymod.api.util.version.VersionMultiRange;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import net.labymod.api.models.version.VersionCompatibility;
import com.google.gson.JsonDeserializer;

public class VersionCompatibilityDeserializer implements JsonDeserializer<VersionCompatibility>
{
    public VersionCompatibility deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return from(json.getAsString());
    }
    
    @NotNull
    public static VersionCompatibility from(@NotNull final String version) {
        return new VersionMultiRange(version);
    }
}
