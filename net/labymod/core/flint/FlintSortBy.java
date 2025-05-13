// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint;

import java.util.Locale;

public enum FlintSortBy
{
    TRENDING, 
    NAME_AZ, 
    NAME_ZA, 
    DOWNLOADS, 
    LATEST, 
    OLDEST, 
    RATING;
    
    private static final FlintSortBy[] VALUES;
    
    public static FlintSortBy[] getValues() {
        return FlintSortBy.VALUES;
    }
    
    @Override
    public String toString() {
        return this.name().toUpperCase(Locale.ENGLISH);
    }
    
    static {
        VALUES = values();
    }
}
