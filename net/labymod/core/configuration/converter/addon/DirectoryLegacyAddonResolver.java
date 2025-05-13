// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.addon;

import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.Iterator;
import java.util.stream.Stream;
import java.io.IOException;
import net.labymod.api.configuration.converter.addon.LoadableLegacyAddon;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.util.zip.ZipFile;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;

public class DirectoryLegacyAddonResolver implements LegacyAddonResolver
{
    private static final Logging LOGGER;
    
    @Override
    public void resolveAddons(final LegacyAddonConverter.Version version, final Collection<LegacyAddon> addons) throws IOException {
        final Path directory = version.getPath();
        if (Files.notExists(directory, new LinkOption[0])) {
            return;
        }
        try (final Stream<Path> files = Files.list(directory)) {
            for (final Path path : files.collect((Collector<? super Path, ?, List<? super Path>>)Collectors.toList())) {
                final String name = path.getFileName().toString();
                if (!name.endsWith(".jar")) {
                    continue;
                }
                try {
                    final ZipFile zip = new ZipFile(path.toFile());
                    try {
                        final ZipEntry addonEntry = zip.getEntry("addon.json");
                        if (addonEntry == null) {
                            DirectoryLegacyAddonResolver.LOGGER.warn("No addon json in LabyMod 3 addon {}", path);
                            zip.close();
                        }
                        else {
                            try (final Reader reader = new InputStreamReader(zip.getInputStream(addonEntry))) {
                                final JsonObject addonJson = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)JsonObject.class);
                                if (addonJson == null || !addonJson.has("uuid") || !addonJson.has("name") || !addonJson.has("mainClass")) {
                                    DirectoryLegacyAddonResolver.LOGGER.warn("Invalid addon json in LabyMod 3 addon {}", path);
                                    reader.close();
                                    zip.close();
                                    continue;
                                }
                                addons.add(new LoadableLegacyAddon(path, version, addonJson.get("uuid").getAsString(), addonJson.get("name").getAsString(), addonJson.get("mainClass").getAsString()));
                            }
                            zip.close();
                        }
                    }
                    catch (final Throwable t2) {
                        try {
                            zip.close();
                        }
                        catch (final Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                        throw t2;
                    }
                }
                catch (final IOException e) {
                    DirectoryLegacyAddonResolver.LOGGER.error("Failed to load legacy addon for version {} from {}", version.getVersion(), path.getFileName(), e);
                }
            }
        }
    }
    
    static {
        LOGGER = Logging.create(DirectoryLegacyAddonResolver.class);
    }
}
