// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

public enum Axis
{
    X, 
    Y, 
    Z;
    
    private static final Axis[] VALUES;
    
    public static Axis[] getValues() {
        return Axis.VALUES;
    }
    
    public static Axis get(final String name) {
        for (final Axis value : Axis.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + Axis.class.getCanonicalName() + "." + name);
    }
    
    public static Axis getOrDefault(final String name, final Axis defaultValue) {
        for (final Axis value : Axis.VALUES) {
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
