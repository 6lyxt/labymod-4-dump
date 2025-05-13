// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.version.parser;

import com.google.gson.JsonObject;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonElement;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Consumer;
import java.net.URL;

public final class VersionParser
{
    public static void parse(final URL url, final Consumer<String> versionConsumer) {
        Objects.requireNonNull(versionConsumer);
        try (final InputStream stream = url.openStream();
             final Reader reader = new InputStreamReader(stream)) {
            final JsonElement element = (JsonElement)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)JsonElement.class);
            if (!element.isJsonObject()) {
                throw constructException("Is not a json object");
            }
            final JsonObject object = element.getAsJsonObject();
            if (!object.has("id")) {
                throw constructException("Property `id` is missing");
            }
            versionConsumer.accept(object.get("id").getAsString());
        }
        catch (final IOException exception) {
            throw new VersionManifestException("Failed to read version manifest", exception);
        }
    }
    
    private static VersionManifestException constructException(final String reason) {
        return new VersionManifestException("Mojang has probably changed the version manifest. (Reason: " + reason + ") Please contact our support");
    }
}
