// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.payload;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Collection;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PayloadRegistry
{
    public static final ResourceLocation MINECRAFT_REGISTER = ResourceLocation.create("minecraft", "register");
    public static final ResourceLocation MINECRAFT_UNREGISTER = ResourceLocation.create("minecraft", "unregister");
    
    default void registerPayloadChannel(final PayloadChannelIdentifier id) {
        this.registerPayloadChannel(ResourceLocation.create(id.getNamespace(), id.getPath()));
    }
    
    void registerPayloadChannel(final ResourceLocation p0);
    
    void unregisterPayloadChannel(final ResourceLocation p0);
    
    boolean canProcessPayload(final ResourceLocation p0);
    
    Collection<ResourceLocation> getRegisteredChannels();
    
    default void forEachRegisteredChannel(final Consumer<ResourceLocation> consumer) {
        for (final ResourceLocation registeredChannel : this.getRegisteredChannels()) {
            if (this.isReservedChannel(registeredChannel)) {
                continue;
            }
            consumer.accept(registeredChannel);
        }
    }
    
    default boolean isReservedChannel(final ResourceLocation id) {
        return PayloadRegistry.MINECRAFT_REGISTER.equals(id) || PayloadRegistry.MINECRAFT_UNREGISTER.equals(id);
    }
}
