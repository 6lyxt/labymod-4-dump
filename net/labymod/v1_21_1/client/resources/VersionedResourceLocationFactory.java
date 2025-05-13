// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.resources;

import net.labymod.api.client.resources.IllegalResourceLocationException;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.core.client.resources.AbstractResourceLocationFactory;

@Singleton
@Implements(ResourceLocationFactory.class)
public class VersionedResourceLocationFactory extends AbstractResourceLocationFactory implements ResourceLocationFactory
{
    @Override
    public ResourceLocation create(@NotNull final String namespace, @NotNull final String path) {
        return this.apply(namespace, path, this::createLocation);
    }
    
    private ResourceLocation createLocation(@NotNull final String namespace, @NotNull final String path) {
        try {
            return (ResourceLocation)akr.a(namespace, path);
        }
        catch (final aa exception) {
            throw new IllegalResourceLocationException((Throwable)exception);
        }
    }
}
