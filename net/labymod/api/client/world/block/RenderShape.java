// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.block;

public enum RenderShape
{
    INVISIBLE, 
    ENTITY_BLOCK_ANIMATED, 
    MODEL;
    
    private static final RenderShape[] VALUES;
    
    public static RenderShape[] getValues() {
        return RenderShape.VALUES;
    }
    
    public static RenderShape get(final String name) {
        for (final RenderShape value : RenderShape.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + RenderShape.class.getCanonicalName() + "." + name);
    }
    
    public static RenderShape getOrDefault(final String name, final RenderShape defaultValue) {
        for (final RenderShape value : RenderShape.VALUES) {
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
