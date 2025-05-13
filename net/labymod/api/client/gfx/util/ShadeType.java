// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.util;

public enum ShadeType
{
    SMOOTH, 
    FLAT;
    
    private static final ShadeType[] VALUES;
    
    public static ShadeType[] getValues() {
        return ShadeType.VALUES;
    }
    
    public static ShadeType get(final String name) {
        for (final ShadeType value : ShadeType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + ShadeType.class.getCanonicalName() + "." + name);
    }
    
    public static ShadeType getOrDefault(final String name, final ShadeType defaultValue) {
        for (final ShadeType value : ShadeType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
    
    static {
        VALUES = values();
    }
}
