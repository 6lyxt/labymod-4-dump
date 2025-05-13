// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.payload;

import net.labymod.core.util.collection.TimestampedValue;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.util.collection.TimestampedCache;

public class LabyModPayloadChannelIdentifierSerializer
{
    private final TimestampedCache<ResourceLocation, PayloadChannelIdentifier> deserialized;
    private final TimestampedCache<PayloadChannelIdentifier, ResourceLocation> serialized;
    
    public LabyModPayloadChannelIdentifierSerializer() {
        this.deserialized = createCache();
        this.serialized = createCache();
    }
    
    private static <K, V> TimestampedCache<K, V> createCache() {
        return new TimestampedCache<K, V>(16, 1800000L, 300000L);
    }
    
    public ResourceLocation serialize(final PayloadChannelIdentifier payloadChannelIdentifier) {
        return this.serialized.computeIfAbsent(payloadChannelIdentifier, identifier -> new TimestampedValue(ResourceLocation.create(identifier.getNamespace(), identifier.getPath()))).getValue();
    }
    
    public PayloadChannelIdentifier deserialize(final ResourceLocation resourceLocation) {
        return this.deserialized.computeIfAbsent(resourceLocation, location -> new TimestampedValue(PayloadChannelIdentifier.create(resourceLocation.getNamespace(), resourceLocation.getPath()))).getValue();
    }
}
