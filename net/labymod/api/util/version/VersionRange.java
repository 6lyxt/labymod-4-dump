// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version;

import net.labymod.api.util.version.exception.VersionException;
import java.util.Locale;
import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.comparison.VersionRangeComparison;
import net.labymod.api.models.version.VersionCompatibility;

public class VersionRange implements VersionCompatibility
{
    private final VersionRangeComparison<Version> comparison;
    
    public VersionRange(@NotNull final String range) {
        this.comparison = VersionRangeComparison.parse(range, VersionDeserializer::from, VersionRange::verifyVersionFormat);
    }
    
    @Override
    public boolean isCompatible(final Version version) {
        return this.comparison.isCompatible(version);
    }
    
    @Override
    public boolean isGreaterThan(final Version version) {
        return this.comparison.isGreaterThan(version);
    }
    
    @Override
    public boolean isLowerThan(final Version version) {
        return this.comparison.isLowerThan(version);
    }
    
    @Override
    public String toString() {
        return this.comparison.toString();
    }
    
    static void verifyVersionFormat(final Version from, final Version to) {
        if (from.getFormat() != to.getFormat()) {
            throw new VersionException(String.format(Locale.ROOT, "Can't create version range between format %s and format %s", from.getFormat().getSimpleName(), to.getFormat().getSimpleName()));
        }
    }
}
