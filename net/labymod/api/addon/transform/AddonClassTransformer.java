// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.transform;

public interface AddonClassTransformer
{
    default void init() {
    }
    
    default boolean shouldTransform(final String name, final String transformedName) {
        return true;
    }
    
    default byte[] transform(final String name, final String transformedName, final byte[] classBytes) {
        return null;
    }
}
