// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target;

public enum RenderTargetMode
{
    BASE, 
    ARB, 
    EXT;
    
    private static final RenderTargetMode[] VALUES;
    
    public static RenderTargetMode[] getValues() {
        return RenderTargetMode.VALUES;
    }
    
    public static RenderTargetMode get(final String name) {
        for (final RenderTargetMode value : RenderTargetMode.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + RenderTargetMode.class.getCanonicalName() + "." + name);
    }
    
    public static RenderTargetMode getOrDefault(final String name, final RenderTargetMode defaultValue) {
        for (final RenderTargetMode value : RenderTargetMode.VALUES) {
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
