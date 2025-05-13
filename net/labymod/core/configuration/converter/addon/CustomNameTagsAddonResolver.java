// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.addon;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import net.labymod.api.configuration.converter.addon.CustomLegacyAddon;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;

public class CustomNameTagsAddonResolver implements LegacyAddonResolver
{
    @Override
    public void resolveAddons(final LegacyAddonConverter.Version version, final Collection<LegacyAddon> addons) throws IOException {
        final Path path = LegacyConverter.LEGACY_PATH.resolve("tags.json");
        if (Files.notExists(path, new LinkOption[0])) {
            return;
        }
        try (final Reader reader = new InputStreamReader(Files.newInputStream(path, new OpenOption[0]))) {
            final JsonObject object = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)JsonObject.class);
            if (object != null && object.has("tags") && object.getAsJsonObject("tags").entrySet().size() != 0) {
                addons.add(new CustomLegacyAddon(version, "CustomNameTags", "customnametags"));
            }
        }
    }
}
