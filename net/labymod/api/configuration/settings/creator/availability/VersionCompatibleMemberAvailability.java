// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.availability;

import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.util.version.comparison.VersionMultiRangeComparison;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.settings.creator.version.def.AddonVersionValidator;
import java.util.HashMap;
import net.labymod.api.configuration.settings.creator.version.VersionValidator;
import java.util.Map;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.logging.Logging;

public class VersionCompatibleMemberAvailability implements MemberAvailability
{
    private static final Logging LOGGER;
    private final LabyAPI labyAPI;
    private final Map<String, VersionValidator> validators;
    
    public VersionCompatibleMemberAvailability(final LabyAPI labyAPI) {
        this.validators = new HashMap<String, VersionValidator>();
        this.labyAPI = labyAPI;
        this.validators.put("addon", new AddonVersionValidator(labyAPI));
    }
    
    @Override
    public boolean isAvailable(final MemberAvailabilityContext context) {
        final MemberInspector inspector = context.inspector();
        final VersionCompatibility annotation = inspector.getAnnotation(VersionCompatibility.class);
        if (annotation == null) {
            return true;
        }
        final String version = annotation.value();
        if (version == null || version.isEmpty()) {
            return true;
        }
        final String type = annotation.type();
        if (type == null) {
            return false;
        }
        if (type.equals("minecraft")) {
            try {
                return this.isMinecraftVersionSupported(version);
            }
            catch (final IllegalArgumentException exception) {
                VersionCompatibleMemberAvailability.LOGGER.error("Version range of a configuration from addon {} could not be parsed", context.namespace(), exception);
                return false;
            }
        }
        final String[] entries = type.split(":", 2);
        final String key = entries[0];
        final VersionValidator selected = this.validators.get(key);
        return selected != null && selected.isSupportedVersion(version, entries[1]);
    }
    
    private boolean isMinecraftVersionSupported(final String version) {
        final MinecraftVersion runningVersion = MinecraftVersions.byId(this.labyAPI.labyModLoader().version().toString());
        if (version.contains("<") || version.contains(",")) {
            final VersionMultiRangeComparison<MinecraftVersion> comparison = VersionMultiRangeComparison.parse(version, MinecraftVersions::byId);
            return comparison.isCompatible(runningVersion);
        }
        final MinecraftVersion desiredVersion = MinecraftVersions.byId(version);
        return runningVersion.isCompatible(desiredVersion);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
