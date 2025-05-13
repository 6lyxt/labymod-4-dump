// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.resources.transform;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.api.event.Event;

public class RegisterResourceTransformerEvent implements Event
{
    private final ResourceTransformerRegistry registry;
    
    public RegisterResourceTransformerEvent(final ResourceTransformerRegistry registry) {
        this.registry = registry;
    }
    
    public void register(final String namespace, final String path, final ResourceTransformer transformer) {
        this.registry.register(namespace, path, transformer);
    }
    
    public void register(final ResourceLocation location, final ResourceTransformer transformer) {
        this.registry.register(location, transformer);
    }
}
