// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;

public class SemanticVersion implements VersionCompatibility, Version
{
    protected int major;
    protected int minor;
    protected int patch;
    protected boolean hasPatch;
    protected String extension;
    
    public SemanticVersion() {
    }
    
    public SemanticVersion(final String version) throws NumberFormatException {
        final String[] parts = version.split("-");
        final String[] numbers = parts[0].split("\\.");
        this.major = ((numbers.length > 0) ? Integer.parseInt(numbers[0]) : 0);
        this.minor = ((numbers.length > 1) ? Integer.parseInt(numbers[1]) : 0);
        if (numbers.length > 2) {
            this.patch = Integer.parseInt(numbers[2]);
            this.hasPatch = true;
        }
        this.extension = ((parts.length > 1) ? parts[1] : null);
    }
    
    public SemanticVersion(final int major, final int minor) {
        this(major, minor, 0);
    }
    
    public SemanticVersion(final int major, final int minor, final int patch) {
        this(major, minor, patch, null);
    }
    
    public SemanticVersion(final int major, final int minor, final int patch, final String extension) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.extension = extension;
    }
    
    @Override
    public int getMajor() {
        return this.major;
    }
    
    @Override
    public int getMinor() {
        return this.minor;
    }
    
    @Override
    public int getPatch() {
        return this.patch;
    }
    
    @Override
    public String getExtension() {
        return this.extension;
    }
    
    @Override
    public boolean isCompatible(final Version version) {
        return this.major == version.getMajor() && this.minor == version.getMinor() && this.patch == version.getPatch() && this.compareExtension(version) && this.compareFormat(version);
    }
    
    @Override
    public boolean isGreaterThan(final Version version) {
        if (!this.compareFormat(version) || !this.compareExtension(version)) {
            return false;
        }
        if (this.major != version.getMajor()) {
            return this.major > version.getMajor();
        }
        if (this.minor != version.getMinor()) {
            return this.minor > version.getMinor();
        }
        return this.patch > version.getPatch();
    }
    
    @Override
    public boolean isLowerThan(final Version version) {
        return !this.isCompatible(version) && !this.isGreaterThan(version) && this.compareExtension(version) && this.compareFormat(version);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SemanticVersion that = (SemanticVersion)o;
        return this.major == that.major && this.minor == that.minor && this.patch == that.patch && this.compareExtension(that) && this.compareFormat(that);
    }
    
    @Override
    public int hashCode() {
        int result = this.major;
        result = 31 * result + this.minor;
        result = 31 * result + this.patch;
        result = 31 * result + this.getFormat().hashCode();
        result = 31 * result + ((this.extension != null) ? this.extension.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        String version = this.major + "." + this.minor;
        if (this.hasPatch) {
            version = version + "." + this.patch;
        }
        if (this.extension != null) {
            version = version + "-" + this.extension;
        }
        return version;
    }
    
    public static boolean isFormat(@NotNull final String version) {
        return version.matches("(\\d+(\\.\\d+){1,2})(\\-[\\w\\d\\.\\-]*)?(\\+[\\w\\d\\.\\-]*)?");
    }
}
