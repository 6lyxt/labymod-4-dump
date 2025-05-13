// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.version;

public interface VersionComparison<T>
{
    boolean isCompatible(final T p0);
    
    boolean isGreaterThan(final T p0);
    
    boolean isLowerThan(final T p0);
}
