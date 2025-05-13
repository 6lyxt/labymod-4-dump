// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.version;

import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.LabyAPI;

public abstract class VersionValidator
{
    protected VersionValidator(final LabyAPI labyAPI) {
    }
    
    public abstract boolean isSupportedVersion(final String p0, final String p1);
    
    protected boolean compareVersion(final String currentVersion, final String version) {
        return this.compareVersion(VersionDeserializer.from(currentVersion), version);
    }
    
    protected boolean compareVersion(final Version currentVersion, final String version) {
        if (version.contains("<") || version.contains(",")) {
            final VersionCompatibility compatibility = VersionCompatibilityDeserializer.from(version);
            return compatibility.isCompatible(currentVersion);
        }
        return currentVersion.isCompatible(VersionDeserializer.from(version));
    }
}
