// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version;

import java.util.Objects;

public interface Version extends VersionCompatibility
{
    int getMajor();
    
    int getMinor();
    
    int getPatch();
    
    String getExtension();
    
    default Class<?> getFormat() {
        return this.getClass();
    }
    
    default boolean compareExtension(final Version version) {
        return Objects.equals(this.getExtension(), version.getExtension());
    }
    
    default boolean compareFormat(final Version version) {
        return Objects.equals(this.getFormat(), version.getFormat());
    }
}
