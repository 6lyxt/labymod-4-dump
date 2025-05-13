// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.version;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;

public class UnknownVersion implements VersionCompatibility, Version
{
    protected final String identifier;
    
    public UnknownVersion(@NotNull final String identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public int getMajor() {
        return 0;
    }
    
    @Override
    public int getMinor() {
        return 0;
    }
    
    @Override
    public int getPatch() {
        return 0;
    }
    
    @Override
    public String getExtension() {
        return this.identifier;
    }
    
    @Override
    public boolean isCompatible(final Version version) {
        return this.equals(version);
    }
    
    @Override
    public boolean isGreaterThan(final Version version) {
        return false;
    }
    
    @Override
    public boolean isLowerThan(final Version version) {
        return false;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final UnknownVersion that = (UnknownVersion)o;
        return Objects.equals(this.identifier, that.identifier);
    }
    
    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }
    
    @Override
    public String toString() {
        return this.identifier;
    }
}
