// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.transform;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourceTransformerRegistry
{
    default void register(final String namespace, final String path, final ResourceTransformer transformer) {
        this.register(ResourceLocation.create(namespace, path), transformer);
    }
    
    void register(final ResourceLocation p0, final ResourceTransformer p1);
}
