// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.payload;

import java.util.Collections;
import java.util.Collection;
import net.labymod.api.event.EventBus;
import java.util.HashSet;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Set;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.network.server.payload.PayloadRegistry;

@Singleton
@Implements(PayloadRegistry.class)
public class DefaultPayloadRegistry implements PayloadRegistry
{
    private final Set<ResourceLocation> payloadChannels;
    
    public DefaultPayloadRegistry() {
        this.payloadChannels = new HashSet<ResourceLocation>();
    }
    
    public void initialize(final EventBus eventBus) {
        eventBus.registerListener(this);
    }
    
    @Override
    public void registerPayloadChannel(final ResourceLocation id) {
        if (this.payloadChannels.contains(id)) {
            return;
        }
        this.payloadChannels.add(id);
    }
    
    @Override
    public void unregisterPayloadChannel(final ResourceLocation id) {
        this.payloadChannels.remove(id);
    }
    
    @Override
    public boolean canProcessPayload(final ResourceLocation id) {
        return this.payloadChannels.contains(id);
    }
    
    @Override
    public Collection<ResourceLocation> getRegisteredChannels() {
        return Collections.unmodifiableCollection((Collection<? extends ResourceLocation>)this.payloadChannels);
    }
}
