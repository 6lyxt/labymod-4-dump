// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Collection;

public final class CastUtil
{
    private CastUtil() {
    }
    
    public static <T> T cast(final Object value) {
        return (T)value;
    }
    
    public static <T extends Collection<?>> T cast(final Collection<?> collection) {
        return (T)collection;
    }
}
