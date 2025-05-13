// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version;

public interface VersionCompatibility extends VersionComparison<Version>
{
    boolean isCompatible(final Version p0);
    
    boolean isGreaterThan(final Version p0);
    
    boolean isLowerThan(final Version p0);
}
