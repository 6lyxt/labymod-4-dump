// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.addon;

import java.nio.file.Paths;
import java.io.IOException;
import net.labymod.api.configuration.converter.addon.CustomLegacyAddon;
import java.util.zip.ZipFile;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import java.util.Collection;
import net.labymod.api.configuration.converter.LegacyAddonConverter;
import java.nio.file.Path;
import net.labymod.api.configuration.converter.addon.LegacyAddonResolver;

public class OptifineLegacyAddonResolver implements LegacyAddonResolver
{
    private static final Path VERSIONS;
    private static final String VERSION_PREFIX = "LabyMod-3-";
    
    @Override
    public void resolveAddons(final LegacyAddonConverter.Version version, final Collection<LegacyAddon> addons) throws IOException {
        Path versionsDirectory = null;
        switch (version) {
            case V1_8: {
                versionsDirectory = OptifineLegacyAddonResolver.VERSIONS.resolve("LabyMod-3-1.8.9");
                break;
            }
            case V1_12: {
                versionsDirectory = OptifineLegacyAddonResolver.VERSIONS.resolve("LabyMod-3-1.12.2");
                break;
            }
            case V1_16: {
                versionsDirectory = OptifineLegacyAddonResolver.VERSIONS.resolve("LabyMod-3-1.16.5");
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(version));
            }
        }
        final Path versionsFile = versionsDirectory.resolve(String.valueOf(versionsDirectory.getFileName()) + ".jar");
        if (Files.notExists(versionsFile, new LinkOption[0])) {
            return;
        }
        try (final ZipFile zip = new ZipFile(versionsFile.toFile())) {
            if (zip.getEntry("changelog.txt") != null && zip.getEntry("buildof.txt") != null) {
                addons.add(new CustomLegacyAddon(version, "OptiFine", "optifine"));
            }
        }
    }
    
    static {
        VERSIONS = Paths.get("versions", new String[0]);
    }
}
