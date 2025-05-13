// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.vertex;

import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.util.CharSequences;
import net.labymod.api.Laby;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.client.gfx.vertex.VertexFormatStorage;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import javax.inject.Inject;
import net.labymod.core.client.gfx.vertex.listener.VertexFormatShaderConstantsConfigureListener;
import java.util.HashMap;
import java.util.HashSet;
import net.labymod.api.event.EventBus;
import java.util.Set;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.vertex.VertexFormatRegistry;

@Singleton
@Implements(VertexFormatRegistry.class)
public class DefaultVertexFormatRegistry implements VertexFormatRegistry
{
    private final ResourceLocationFactory resourceLocationFactory;
    private final Map<ResourceLocation, VertexFormat> vertexFormatMap;
    private final Set<String> loadedServices;
    
    @Inject
    public DefaultVertexFormatRegistry(final EventBus eventBus, final ResourceLocationFactory resourceLocationFactory) {
        this.loadedServices = new HashSet<String>();
        this.resourceLocationFactory = resourceLocationFactory;
        this.vertexFormatMap = new HashMap<ResourceLocation, VertexFormat>();
        eventBus.registerListener(new VertexFormatShaderConstantsConfigureListener());
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onServiceLoad(final ServiceLoadEvent event) {
        final CustomServiceLoader<VertexFormatStorage> storages = event.load(VertexFormatStorage.class, CustomServiceLoader.ServiceType.ADVANCED);
        final Map<ResourceLocation, VertexFormat> formats = new HashMap<ResourceLocation, VertexFormat>();
        for (final VertexFormatStorage storage : storages) {
            final String name = storage.getClass().getName();
            if (this.loadedServices.contains(name)) {
                continue;
            }
            this.loadedServices.add(name);
            final String namespace = Laby.labyAPI().getNamespace(storage);
            final Map<String, VertexFormat> vertexFormats = new HashMap<String, VertexFormat>();
            storage.store(vertexFormats);
            for (final Map.Entry<String, VertexFormat> entry : vertexFormats.entrySet()) {
                String key = entry.getKey();
                key = (String)CharSequences.toLowerCase(key);
                formats.put(ResourceLocation.create(namespace, key), entry.getValue());
            }
        }
        this.vertexFormatMap.putAll(formats);
    }
    
    @Nullable
    @Override
    public VertexFormat getVertexFormat(final String name) {
        return this.getVertexFormat(this.resourceLocationFactory.parse(name));
    }
    
    @Nullable
    @Override
    public VertexFormat getVertexFormat(final ResourceLocation location) {
        return this.vertexFormatMap.get(location);
    }
    
    @Override
    public Collection<VertexFormat> getVertexFormats() {
        return this.vertexFormatMap.values();
    }
}
