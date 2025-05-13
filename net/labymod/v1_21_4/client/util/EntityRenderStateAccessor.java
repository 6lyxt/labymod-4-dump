// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.util;

public interface EntityRenderStateAccessor<T>
{
    default <E> EntityRenderStateAccessor<E> self(final Object obj) {
        if (obj instanceof final EntityRenderStateAccessor accessor) {
            return accessor;
        }
        return null;
    }
    
    T labyMod$getEntity();
    
    void labyMod$setEntity(final T p0);
}
