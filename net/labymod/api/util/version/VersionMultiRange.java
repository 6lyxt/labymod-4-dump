// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version;

import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.comparison.VersionMultiRangeComparison;
import net.labymod.api.models.version.VersionCompatibility;

public class VersionMultiRange implements VersionCompatibility
{
    private final VersionMultiRangeComparison<Version> comparison;
    
    public VersionMultiRange(@NotNull final String ranges) {
        this.comparison = VersionMultiRangeComparison.parse(ranges, VersionDeserializer::from, VersionRange::verifyVersionFormat);
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
}
